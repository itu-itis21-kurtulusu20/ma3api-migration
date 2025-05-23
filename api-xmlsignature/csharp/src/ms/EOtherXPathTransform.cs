﻿using System;
using System.Collections;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Security.Cryptography;
using System.Security.Cryptography.Xml;
using System.Text;
using System.Xml;
using System.Xml.XPath;
using System.Xml.Xsl;

namespace tr.gov.tubitak.uekae.esya.api.xmlsignature.ms
{

    // www.w3.org/TR/xmldsig-core/
    // see Section 6.6.3 of the XMLDSIG specification
    public class EOtherXPathTransform : Transform
    {
        private Type[] input;
        private Type[] output;
        private XmlNodeList xpath;
        private XmlDocument doc;
        private XsltContext ctx;

        public EOtherXPathTransform()
        {
            Algorithm = "http://www.w3.org/TR/1999/REC-xpath-19991116";
        }
        public override Type[] InputTypes
        {
            get
            {
                if (input == null)
                {
                    lock (this)
                    {
                        // this way the result is cached if called multiple time
                        input = new Type[3];
                        input[0] = typeof(System.IO.Stream);
                        input[1] = typeof(System.Xml.XmlDocument);
                        input[2] = typeof(System.Xml.XmlNodeList);
                    }
                }
                return input;
            }
        }
        public override Type[] OutputTypes
        {
            get
            {
                if (output == null)
                {
                    lock (this)
                    {
                        // this way the result is cached if called multiple time
                        output = new Type[1];
                        output[0] = typeof(System.Xml.XmlNodeList);
                    }
                }
                return output;
            }
        }
        protected override XmlNodeList GetInnerXml()
        {
            return xpath;
        }

        public override object GetOutput()
        {
            if (xpath == null)
                return new XmlDsigNodeList(new ArrayList());
            // evaluate every time since input or xpath might have changed.
            string x = null;
            for (int i = 0; i < xpath.Count; i++)
            {
                switch (xpath[i].NodeType)
                {
                    case XmlNodeType.Text:
                    case XmlNodeType.CDATA:
                    case XmlNodeType.Element:
                        x += xpath[i].InnerText;
                        break;
                }
            }
            ctx = new XmlDsigXPathContext(doc);
            foreach (XmlNode n in xpath)
            {
                XPathNavigator nav = n.CreateNavigator();
                XPathNodeIterator iter = nav.Select("namespace::*");
                while (iter.MoveNext())
                    if (iter.Current.LocalName != "xml")
                        ctx.AddNamespace(iter.Current.LocalName, iter.Current.Value);
            }
            return EvaluateMatch(doc, x);
        }
        public override object GetOutput(Type type)
        {
            if (type != typeof(XmlNodeList))
                throw new ArgumentException("type");
            return GetOutput();
        }
        private XmlDsigNodeList EvaluateMatch(XmlNode n, string xpath)
        {
            ArrayList al = new ArrayList();
            // Strictly to say, document node is explicitly
            // excluded by W3C spec (context node is initialized
            // to the document root and XPath expression is
            // "//. | //@* | //namespace::*)
            XPathNavigator nav = n.CreateNavigator();
            XPathExpression exp = nav.Compile(xpath);
            exp.SetContext(ctx);
            EvaluateMatch(n, exp, al);
            return new XmlDsigNodeList(al);
        }
        private void EvaluateMatch(XmlNode n, XPathExpression exp, ArrayList al)
        {
            if (NodeMatches(n, exp))
                al.Add(n);
            if (n.Attributes != null)
                for (int i = 0; i < n.Attributes.Count; i++)
                    if (NodeMatches(n.Attributes[i], exp))
                        al.Add(n.Attributes[i]);
            for (int i = 0; i < n.ChildNodes.Count; i++)
                EvaluateMatch(n.ChildNodes[i], exp, al);
        }
        private bool NodeMatches(XmlNode n, XPathExpression exp)
        {
            // This looks waste of memory since it creates 
            // XPathNavigator every time, but even if we use
            //  XPathNodeIterator.Current, it also clones every time.
            object ret = n.CreateNavigator().Evaluate(exp);
            if (ret is bool)
                return (bool)ret;
            if (ret is double)
            {
                double d = (double)ret;
                return (d > 0.0 || !double.IsNaN(d));
            }
            if (ret is string)
                return ((string)ret).Length > 0;
            if (ret is XPathNodeIterator)
            {
                XPathNodeIterator retiter = (XPathNodeIterator)ret;
                return retiter.Count > 0;
            }
            return false;
        }
        public override void LoadInnerXml(XmlNodeList nodeList)
        {
            if (nodeList == null)
                throw new CryptographicException("nodeList");
            xpath = nodeList;
        }
        public override void LoadInput(object obj)
        {
            if (obj is Stream)
            {
                doc = new XmlDocument();
                //doc.XmlResolver = Resolver);
                doc.Load(obj as Stream);
            }
            else if (obj is XmlDocument)
            {
                doc = (obj as XmlDocument);
            }
            else if (obj is XmlNodeList)
            {
                doc = new XmlDocument();
                foreach (XmlNode xn in (obj as XmlNodeList))
                {
                    XmlNode importedNode = doc.ImportNode(xn, true);
                    doc.AppendChild(importedNode);
                }
                //doc.XmlResolver = GetResolver();
            }
        }


        // Internal classes to support XPath extension function here()
        internal class XmlDsigXPathContext : XsltContext
        {
            readonly XmlDsigXPathFunctionHere here;
            public XmlDsigXPathContext(XmlNode node)
            {
                here = new XmlDsigXPathFunctionHere(node);
            }
            public override IXsltContextFunction ResolveFunction(
            string prefix, string name, XPathResultType[] argType)
            {
                // Here MS.NET incorrectly allows arbitrary
                // name e.g. "heretic()".
                if (name == "here" &&
                prefix == String.Empty &&
                argType.Length == 0)
                    return here;
                else
                    return null; // ????
            }
            public override bool Whitespace
            {
                get { return true; }
            }
            public override bool PreserveWhitespace(XPathNavigator node)
            {
                return true;
            }
            public override int CompareDocument(string s1, string s2)
            {
                return String.Compare(s1, s2);
            }
            public override IXsltContextVariable ResolveVariable(string prefix, string name)
            {
                throw new InvalidOperationException();
            }
        }
        internal class XmlDsigXPathFunctionHere : IXsltContextFunction
        {
            // Static
            static XPathResultType[] types;
            static XmlDsigXPathFunctionHere()
            {
                types = new XPathResultType[0];
            }
            // Instance
            readonly XPathNodeIterator xpathNode;
            public XmlDsigXPathFunctionHere(XmlNode node)
            {
                xpathNode = node.CreateNavigator().Select(".");
            }
            public XPathResultType[] ArgTypes
            {
                get { return types; }
            }
            public int Maxargs { get { return 0; } }
            public int Minargs { get { return 0; } }
            public XPathResultType ReturnType
            {
                get { return XPathResultType.NodeSet; }
            }
            public object Invoke(XsltContext ctx, object[] args, XPathNavigator docContext)
            {
                if (args.Length != 0)
                    throw new ArgumentException("Not allowed arguments for function here().", "args");
                return xpathNode.Clone();
            }
        }
    }
}
