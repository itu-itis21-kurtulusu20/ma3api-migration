// 
// This file was generated by the Objective Systems ASN1C Compiler
// (http://www.obj-sys.com).  Version: 6.6.4, Date: 10-Oct-2013.
// 
using System;
using Com.Objsys.Asn1.Runtime;

namespace tr.gov.tubitak.uekae.esya.asn.x509
{

    public class _ExplicitValues
    {
        public static readonly long common_name = 1;
        public static readonly long teletex_common_name = 2;
        public static readonly long teletex_organization_name = 3;
        public static readonly long teletex_personal_name = 4;
        public static readonly long teletex_organizational_unit_names = 5;
        public static readonly long pds_name = 7;
        public static readonly long physical_delivery_country_name = 8;
        public static readonly long postal_code = 9;
        public static readonly long physical_delivery_office_name = 10;
        public static readonly long physical_delivery_office_number = 11;
        public static readonly long extension_OR_address_components = 12;
        public static readonly long physical_delivery_personal_name = 13;
        public static readonly long physical_delivery_organization_name = 14;
        public static readonly long extension_physical_delivery_address_components = 15;
        public static readonly long unformatted_postal_address = 16;
        public static readonly long street_address = 17;
        public static readonly long post_office_box_address = 18;
        public static readonly long poste_restante_address = 19;
        public static readonly long unique_postal_name = 20;
        public static readonly long local_postal_attributes = 21;
        public static readonly long extended_network_address = 22;
        public static readonly long terminal_type = 23;
        public static readonly long teletex_domain_defined_attributes = 6;
        public static readonly long ub_name = 32768;
        public static readonly long ub_common_name = 256;
        public static readonly long ub_locality_name = 128;
        public static readonly long ub_state_name = 128;
        public static readonly long ub_organization_name = 256;
        public static readonly long ub_organizational_unit_name = 256;
        public static readonly long ub_title = 128;
        public static readonly long ub_serial_number = 64;
        public static readonly long ub_match = 128;
        public static readonly long ub_emailaddress_length = 128;
        public static readonly long ub_common_name_length = 256;
        public static readonly long ub_country_name_alpha_length = 2;
        public static readonly long ub_country_name_numeric_length = 3;
        public static readonly long ub_domain_defined_attributes = 4;
        public static readonly long ub_domain_defined_attribute_type_length = 8;
        public static readonly long ub_domain_defined_attribute_value_length = 128;
        public static readonly long ub_domain_name_length = 16;
        public static readonly long ub_extension_attributes = 256;
        public static readonly long ub_e163_4_number_length = 15;
        public static readonly long ub_e163_4_sub_address_length = 40;
        public static readonly long ub_generation_qualifier_length = 3;
        public static readonly long ub_given_name_length = 16;
        public static readonly long ub_initials_length = 5;
        public static readonly long ub_integer_options = 256;
        public static readonly long ub_numeric_user_id_length = 32;
        public static readonly long ub_organization_name_length = 256;
        public static readonly long ub_organizational_unit_name_length = 256;
        public static readonly long ub_organizational_units = 4;
        public static readonly long ub_pds_name_length = 16;
        public static readonly long ub_pds_parameter_length = 30;
        public static readonly long ub_pds_physical_address_lines = 6;
        public static readonly long ub_postal_code_length = 16;
        public static readonly long ub_pseudonym = 128;
        public static readonly long ub_surname_length = 40;
        public static readonly long ub_terminal_id_length = 24;
        public static readonly long ub_unformatted_address_length = 180;
        public static readonly long ub_x121_address_length = 16;
        public static readonly long ub_organization_identifier_name_length = 256;
        public static readonly int[] id_pkix = { 1, 3, 6, 1, 5, 5, 7 };
        public static readonly int[] id_pe = { 1, 3, 6, 1, 5, 5, 7, 1 };
        public static readonly int[] id_qt = { 1, 3, 6, 1, 5, 5, 7, 2 };
        public static readonly int[] id_kp = { 1, 3, 6, 1, 5, 5, 7, 3 };
        public static readonly int[] id_ad = { 1, 3, 6, 1, 5, 5, 7, 48 };
        public static readonly int[] id_ad_ocsp = { 1, 3, 6, 1, 5, 5, 7, 48, 1 };
        public static readonly int[] id_ad_caIssuers = { 1, 3, 6, 1, 5, 5, 7, 48, 2 };
        public static readonly int[] id_ad_timeStamping = { 1, 3, 6, 1, 5, 5, 7, 48, 3 };
        public static readonly int[] id_ad_caRepository = { 1, 3, 6, 1, 5, 5, 7, 48, 5 };
        public static readonly int[] id_qt_cps = { 1, 3, 6, 1, 5, 5, 7, 2, 1 };
        public static readonly int[] id_qt_unotice = { 1, 3, 6, 1, 5, 5, 7, 2, 2 };
        public static readonly int[] id_at_name = { 2, 5, 4, 41 };
        public static readonly int[] id_at_surname = { 2, 5, 4, 4 };
        public static readonly int[] id_at_givenName = { 2, 5, 4, 42 };
        public static readonly int[] id_at_commonName = { 2, 5, 4, 3 };
        public static readonly int[] id_at_localityName = { 2, 5, 4, 7 };
        public static readonly int[] id_at_stateOrProvinceName = { 2, 5, 4, 8 };
        public static readonly int[] id_at_organizationName = { 2, 5, 4, 10 };
        public static readonly int[] id_at_organizationalUnitName = { 2, 5, 4, 11 };
        public static readonly int[] id_at_title = { 2, 5, 4, 12 };
        public static readonly int[] id_at_countryName = { 2, 5, 4, 6 };
        public static readonly int[] id_at_serialNumber = { 2, 5, 4, 5 };
        public static readonly long ub_ucube_name = 128;
        public static readonly int[] id_at_pseudonym = { 2, 5, 4, 65 };
        public static readonly int[] id_domainComponent = { 0, 9, 2342, 19200300, 100, 1, 25 };
        public static readonly int[] pkcs_9 = { 1, 2, 840, 113549, 1, 9 };
        public static readonly int[] id_emailAddress = { 1, 2, 840, 113549, 1, 9, 1 };
        public static readonly int[] id_at_organizationIdentifier = { 2, 5, 4, 97 };

        /*
        Licensed copy*/
        public static readonly byte[] _rtkey = new byte[]{
         (byte)0xc7, (byte)0xfa, (byte)0x2a, (byte)0xc1, 
         (byte)0xde, (byte)0x81, (byte)0xf5, (byte)0x87, 
         (byte)0x70, (byte)0xdb, (byte)0x25, (byte)0xe1, 
         (byte)0x7b, (byte)0xbc, (byte)0x20, (byte)0xfe, 
         (byte)0xe9, (byte)0x4f, (byte)0xa9, (byte)0xf7, 
         (byte)0x8b, (byte)0x30, (byte)0xf2, (byte)0x3a, 
         (byte)0x22, (byte)0x56, (byte)0x45, (byte)0xd4, 
         (byte)0xda, (byte)0x29, (byte)0x12, (byte)0xe3, 
         (byte)0x3f, (byte)0x17, (byte)0x8f, (byte)0x5f, 
         (byte)0x4e, (byte)0x87, (byte)0x91, (byte)0xc1, 
         (byte)0x60, (byte)0x31, (byte)0xb0, (byte)0xd0, 
         (byte)0xb5, (byte)0xde, (byte)0x53, (byte)0x14, 
         (byte)0x3d, (byte)0x63, (byte)0x72, (byte)0xf0, 
         (byte)0x11, (byte)0x8e, (byte)0x3f, (byte)0x59, 
         (byte)0x8f, (byte)0x86, (byte)0x0c, (byte)0x79, 
         (byte)0x91, (byte)0xa5, (byte)0x23, (byte)0x76
      };
    }
}
