package test.esya.api.crypto.provider.gnu;

import junit.framework.TestCase;
import tr.gov.tubitak.uekae.esya.api.common.util.StringUtil;
import tr.gov.tubitak.uekae.esya.api.crypto.BufferedCipher;
import tr.gov.tubitak.uekae.esya.api.crypto.Crypto;
import tr.gov.tubitak.uekae.esya.api.crypto.alg.CipherAlg;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPrivateCrtKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.ArrayList;
import java.util.Arrays;

public class EME_OAEPTest extends TestCase{
	
	public static byte[] SALT = null;
	
	private static ArrayList<byte[]> decrypt(KeyPair aKP, ArrayList<byte[]> aCiphers)
	throws Exception
	{
		ArrayList<byte []> decryptedValues = new ArrayList<byte[]>();
		
		for (byte[] cipher : aCiphers) 
		{
			 BufferedCipher simKrip = Crypto.getDecryptor(CipherAlg.RSA_OAEP);
		     simKrip.init(aKP.getPrivate().getEncoded(), null);
		     byte[] decrypted = simKrip.doFinal(cipher);
		     decryptedValues.add(decrypted);
		}
		
		return decryptedValues;
	}
	
	private static ArrayList<byte[]> add(byte[] a1,byte[] a2,byte[] a3,byte[] a4,byte[] a5,byte[] a6)
	{
		ArrayList<byte[]> list = new ArrayList<byte[]>();
		list.add(a1);
		list.add(a2);
		list.add(a3);
		list.add(a4);
		list.add(a5);
		list.add(a6);
		
		return list;
	}
	
	public static void test1024key()
	throws Exception
	{
		RSAPublicKeySpec pubs = new RSAPublicKeySpec(new BigInteger("a8b3b284af8eb50b387034a860f146c4919f318763cd6c5598c8ae4811a1e0abc4c7e0b082d693a5e7fced675cf4668512772c0cbc64a742c6c630f533c8cc72f62ae833c40bf25842e984bb78bdbf97c0107d55bdb662f5c4e0fab9845cb5148ef7392dd3aaff93ae1e6b667bb3d4247616d4f5ba10d4cfd226de88d39f16fb",16),
                new BigInteger("010001",16));
		
		RSAPrivateCrtKeySpec pris = new RSAPrivateCrtKeySpec(new BigInteger("a8b3b284af8eb50b387034a860f146c4919f318763cd6c5598c8ae4811a1e0abc4c7e0b082d693a5e7fced675cf4668512772c0cbc64a742c6c630f533c8cc72f62ae833c40bf25842e984bb78bdbf97c0107d55bdb662f5c4e0fab9845cb5148ef7392dd3aaff93ae1e6b667bb3d4247616d4f5ba10d4cfd226de88d39f16fb",16),
                new BigInteger("010001",16),
                new BigInteger("53339cfdb79fc8466a655c7316aca85c55fd8f6dd898fdaf119517ef4f52e8fd8e258df93fee180fa0e4ab29693cd83b152a553d4ac4d1812b8b9fa5af0e7f55fe7304df41570926f3311f15c4d65a732c483116ee3d3d2d0af3549ad9bf7cbfb78ad884f84d5beb04724dc7369b31def37d0cf539e9cfcdd3de653729ead5d1",16),
                new BigInteger("d32737e7267ffe1341b2d5c0d150a81b586fb3132bed2f8d5262864a9cb9f30af38be448598d413a172efb802c21acf1c11c520c2f26a471dcad212eac7ca39d",16),
                new BigInteger("cc8853d1d54da630fac004f471f281c7b8982d8224a490edbeb33d3e3d5cc93c4765703d1dd791642f1f116a0dd852be2419b2af72bfe9a030e860b0288b5d77",16),
                new BigInteger("0e12bf1718e9cef5599ba1c3882fe8046a90874eefce8f2ccc20e4f2741fb0a33a3848aec9c9305fbecbd2d76819967d4671acc6431e4037968db37878e695c1",16),
                new BigInteger("95297b0f95a2fa67d00707d609dfd4fc05c89dafc2ef6d6ea55bec771ea333734d9251e79082ecda866efef13c459e1a631386b7e354c899f5f112ca85d71583",16),
                new BigInteger("4f456c502493bdc0ed2ab756a3a6ed4d67352a697d4216e93212b127a63d5411ce6fa98d5dbefd73263e3728142743818166ed7dd63687dd2a8ca1d2f4fbd8e1",16));

		
		 byte[] msg1 = StringUtil.toByteArray("6628194e12073db03ba94cda9ef9532397d50dba79b987004afefe34");
		 byte[] msg2 = StringUtil.toByteArray("750c4047f547e8e41411856523298ac9bae245efaf1397fbe56f9dd5");
		 byte[] msg3 = StringUtil.toByteArray("d94ae0832e6445ce42331cb06d531a82b1db4baad30f746dc916df24d4e3c2451fff59a6423eb0e1d02d4fe646cf699dfd818c6e97b051");
		 byte[] msg4 = StringUtil.toByteArray("52e650d98e7f2a048b4f86852153b97e01dd316f346a19f67a85");
		 byte[] msg5 = StringUtil.toByteArray("8da89fd9e5f974a29feffb462b49180f6cf9e802");
		 byte[] msg6 = StringUtil.toByteArray("26521050844271");
		 
		 
		
		 byte[] enc1 = StringUtil.toByteArray("354fe67b4a126d5d35fe36c777791a3f7ba13def484e2d3908aff722fad468fb21696de95d0be911c2d3174f8afcc201035f7b6d8e69402de5451618c21a535fa9d7bfc5b8dd9fc243f8cf927db31322d6e881eaa91a996170e657a05a266426d98c88003f8477c1227094a0d9fa1e8c4024309ce1ecccb5210035d47ac72e8a");
		 byte[] enc2 = StringUtil.toByteArray("640db1acc58e0568fe5407e5f9b701dff8c3c91e716c536fc7fcec6cb5b71c1165988d4a279e1577d730fc7a29932e3f00c81515236d8d8e31017a7a09df4352d904cdeb79aa583adcc31ea698a4c05283daba9089be5491f67c1a4ee48dc74bbbe6643aef846679b4cb395a352d5ed115912df696ffe0702932946d71492b44");
		 byte[] enc3 = StringUtil.toByteArray("423736ed035f6026af276c35c0b3741b365e5f76ca091b4e8c29e2f0befee603595aa8322d602d2e625e95eb81b2f1c9724e822eca76db8618cf09c5343503a4360835b5903bc637e3879fb05e0ef32685d5aec5067cd7cc96fe4b2670b6eac3066b1fcf5686b68589aafb7d629b02d8f8625ca3833624d4800fb081b1cf94eb");
		 byte[] enc4 = StringUtil.toByteArray("45ead4ca551e662c9800f1aca8283b0525e6abae30be4b4aba762fa40fd3d38e22abefc69794f6ebbbc05ddbb11216247d2f412fd0fba87c6e3acd888813646fd0e48e785204f9c3f73d6d8239562722dddd8771fec48b83a31ee6f592c4cfd4bc88174f3b13a112aae3b9f7b80e0fc6f7255ba880dc7d8021e22ad6a85f0755");
		 byte[] enc5 = StringUtil.toByteArray("36f6e34d94a8d34daacba33a2139d00ad85a9345a86051e73071620056b920e219005855a213a0f23897cdcd731b45257c777fe908202befdd0b58386b1244ea0cf539a05d5d10329da44e13030fd760dcd644cfef2094d1910d3f433e1c7c6dd18bc1f2df7f643d662fb9dd37ead9059190f4fa66ca39e869c4eb449cbdc439");
		 byte[] enc6 = StringUtil.toByteArray("42cee2617b1ecea4db3f4829386fbd61dafbf038e180d837c96366df24c097b4ab0fac6bdf590d821c9f10642e681ad05b8d78b378c0f46ce2fad63f74e0ad3df06b075d7eb5f5636f8d403b9059ca761b5c62bb52aa45002ea70baace08ded243b9d8cbd62a68ade265832b56564e43a6fa42ed199a099769742df1539e8255");
		 
		 
		ArrayList<byte[]> ciphers = add(enc1, enc2, enc3, enc4, enc5, enc6);
		ArrayList<byte[]> data = add(msg1, msg2, msg3, msg4, msg5, msg6);
		 
		 
		 KeyFactory kf = KeyFactory.getInstance("RSA");
		 PrivateKey prik = kf.generatePrivate(pris);
		 PublicKey pubk = kf.generatePublic(pubs);
		 
		 
		 ArrayList<byte[]> results = decrypt(new KeyPair(pubk, prik), ciphers);
		 
	
		 compare(results, data);
	}
	
	
	public static void test1025key()
	throws Exception
	{
		RSAPublicKeySpec pubs = new RSAPublicKeySpec(new BigInteger("01947c7fce90425f47279e70851f25d5e62316fe8a1df19371e3e628e260543e4901ef6081f68c0b8141190d2ae8daba7d1250ec6db636e944ec3722877c7c1d0a67f14b1694c5f0379451a43e49a32dde83670b73da91a1c99bc23b436a60055c610f0baf99c1a079565b95a3f1526632d1d4da60f20eda25e653c4f002766f45",16),
                new BigInteger("010001",16));
		
		RSAPrivateCrtKeySpec pris = new RSAPrivateCrtKeySpec(new BigInteger("01947c7fce90425f47279e70851f25d5e62316fe8a1df19371e3e628e260543e4901ef6081f68c0b8141190d2ae8daba7d1250ec6db636e944ec3722877c7c1d0a67f14b1694c5f0379451a43e49a32dde83670b73da91a1c99bc23b436a60055c610f0baf99c1a079565b95a3f1526632d1d4da60f20eda25e653c4f002766f45",16),
                new BigInteger("010001",16),
                new BigInteger("0823f20fadb5da89088a9d00893e21fa4a1b11fbc93c64a3be0baaea97fb3b93c3ff713704c19c963c1d107aae99054739f79e02e186de86f87a6ddefea6d8ccd1d3c81a47bfa7255be20601a4a4b2f08a167b5e279d715b1b455bdd7eab245941d9768b9acefb3ccda5952da3cee72525b4501663a8ee15c9e992d92462fe39",16),
                new BigInteger("0159dbde04a33ef06fb608b80b190f4d3e22bcc13ac8e4a081033abfa416edb0b338aa08b57309ea5a5240e7dc6e54378c69414c31d97ddb1f406db3769cc41a43",16),
                new BigInteger("012b652f30403b38b40995fd6ff41a1acc8ada70373236b7202d39b2ee30cfb46db09511f6f307cc61cc21606c18a75b8a62f822df031ba0df0dafd5506f568bd7",16),
                new BigInteger("436ef508de736519c2da4c580d98c82cb7452a3fb5efadc3b9c7789a1bc6584f795addbbd32439c74686552ecb6c2c307a4d3af7f539eec157248c7b31f1a255",16),
                new BigInteger("012b15a89f3dfb2b39073e73f02bdd0c1a7b379dd435f05cdde2eff9e462948b7cec62ee9050d5e0816e0785a856b49108dcb75f3683874d1ca6329a19013066ff",16),
                new BigInteger("0270db17d5914b018d76118b24389a7350ec836b0063a21721236fd8edb6d89b51e7eeb87b611b7132cb7ea7356c23151c1e7751507c786d9ee1794170a8c8e8",16));

		
		 byte[] msg1 = StringUtil.toByteArray("8ff00caa605c702830634d9a6c3d42c652b58cf1d92fec570beee7");
		 byte[] msg2 = StringUtil.toByteArray("2d");
		 byte[] msg3 = StringUtil.toByteArray("74fc88c51bc90f77af9d5e9a4a70133d4b4e0b34da3c37c7ef8e");
		 byte[] msg4 = StringUtil.toByteArray("a7eb2a5036931d27d4e891326d99692ffadda9bf7efd3e34e622c4adc085f721dfe885072c78a203b151739be540fa8c153a10f00a");
		 byte[] msg5 = StringUtil.toByteArray("2ef2b066f854c33f3bdcbb5994a435e73d6c6c");
		 byte[] msg6 = StringUtil.toByteArray("8a7fb344c8b6cb2cf2ef1f643f9a3218f6e19bba89c0");
		 
		 
		
		 byte[] enc1 = StringUtil.toByteArray("0181af8922b9fcb4d79d92ebe19815992fc0c1439d8bcd491398a0f4ad3a329a5bd9385560db532683c8b7da04e4b12aed6aacdf471c34c9cda891addcc2df3456653aa6382e9ae59b54455257eb099d562bbe10453f2b6d13c59c02e10f1f8abb5da0d0570932dacf2d0901db729d0fefcc054e70968ea540c81b04bcaefe720e");
		 byte[] enc2 = StringUtil.toByteArray("018759ff1df63b2792410562314416a8aeaf2ac634b46f940ab82d64dbf165eee33011da749d4bab6e2fcd18129c9e49277d8453112b429a222a8471b070993998e758861c4d3f6d749d91c4290d332c7a4ab3f7ea35ff3a07d497c955ff0ffc95006b62c6d296810d9bfab024196c7934012c2df978ef299aba239940cba10245");
		 byte[] enc3 = StringUtil.toByteArray("018802bab04c60325e81c4962311f2be7c2adce93041a00719c88f957575f2c79f1b7bc8ced115c706b311c08a2d986ca3b6a9336b147c29c6f229409ddec651bd1fdd5a0b7f610c9937fdb4a3a762364b8b3206b4ea485fd098d08f63d4aa8bb2697d027b750c32d7f74eaf5180d2e9b66b17cb2fa55523bc280da10d14be2053");
		 byte[] enc4 = StringUtil.toByteArray("00a4578cbc176318a638fba7d01df15746af44d4f6cd96d7e7c495cbf425b09c649d32bf886da48fbaf989a2117187cafb1fb580317690e3ccd446920b7af82b31db5804d87d01514acbfa9156e782f867f6bed9449e0e9a2c09bcecc6aa087636965e34b3ec766f2fe2e43018a2fddeb140616a0e9d82e5331024ee0652fc7641");
		 byte[] enc5 = StringUtil.toByteArray("00ebc5f5fda77cfdad3c83641a9025e77d72d8a6fb33a810f5950f8d74c73e8d931e8634d86ab1246256ae07b6005b71b7f2fb98351218331ce69b8ffbdc9da08bbc9c704f876deb9df9fc2ec065cad87f9090b07acc17aa7f997b27aca48806e897f771d95141fe4526d8a5301b678627efab707fd40fbebd6e792a25613e7aec");
		 byte[] enc6 = StringUtil.toByteArray("010839ec20c27b9052e55befb9b77e6fc26e9075d7a54378c646abdf51e445bd5715de81789f56f1803d9170764a9e93cb78798694023ee7393ce04bc5d8f8c5a52c171d43837e3aca62f609eb0aa5ffb0960ef04198dd754f57f7fbe6abf765cf118b4ca443b23b5aab266f952326ac4581100644325f8b721acd5d04ff14ef3a");
		 
		 
		ArrayList<byte[]> ciphers = add(enc1, enc2, enc3, enc4, enc5, enc6);
		ArrayList<byte[]> data = add(msg1, msg2, msg3, msg4, msg5, msg6);
		 
		 
		 KeyFactory kf = KeyFactory.getInstance("RSA");
		 PrivateKey prik = kf.generatePrivate(pris);
		 PublicKey pubk = kf.generatePublic(pubs);
		
		 
		 ArrayList<byte[]> results = decrypt(new KeyPair(pubk, prik),  ciphers);
		 
	
		 compare(results, data);
	}
	
	
	public static void test1026key()
	throws Exception
	{
		RSAPublicKeySpec pubs = new RSAPublicKeySpec(new BigInteger("02b58fec039a860700a4d7b6462f93e6cdd491161ddd74f4e810b40e3c1652006a5c277b2774c11305a4cbab5a78efa57e17a86df7a3fa36fc4b1d2249f22ec7c2dd6a463232accea906d66ebe80b5704b10729da6f833234abb5efdd4a292cbfad33b4d33fa7a14b8c397b56e3acd21203428b77cdfa33a6da706b3d8b0fc43e9",16),
               new BigInteger("010001",16));
		
		RSAPrivateCrtKeySpec pris = new RSAPrivateCrtKeySpec(new BigInteger("02b58fec039a860700a4d7b6462f93e6cdd491161ddd74f4e810b40e3c1652006a5c277b2774c11305a4cbab5a78efa57e17a86df7a3fa36fc4b1d2249f22ec7c2dd6a463232accea906d66ebe80b5704b10729da6f833234abb5efdd4a292cbfad33b4d33fa7a14b8c397b56e3acd21203428b77cdfa33a6da706b3d8b0fc43e9",16),
                new BigInteger("010001",16),
                new BigInteger("15b48a5b5683a94670e23b5718f814fa0e13f85038f50711182cba61510581f3d22c7e232ef937e22e551d68b86e2f8cb1aad8be2e488f5df7efd279e3f568d4eaf36f80cf7141ace60fcc9113fb6c4a841fd50bbc7c512ffcbeff21487aa811eb3ca8c62005346a86de86bfa1d8a948fd3f348c22eaadf333c3ce6ce13208fd",16),
                new BigInteger("01bf01d216d73595cf0270c2beb78d40a0d8447d31da919a983f7eea781b77d85fe371b3e9373e7b69217d3150a02d8958de7fad9d555160958b4454127e0e7eaf",16),
                new BigInteger("018d3399658166db3829816d7b295416759e9c91987f5b2d8aecd63b04b48bd7b2fcf229bb7f8a6dc88ba13dd2e39ad55b6d1a06160708f9700be80b8fd3744ce7",16),
                new BigInteger("06c0a249d20a6f2ee75c88b494d53f6aae99aa427c88c28b163a769445e5f390cf40c274fd6ea6329a5ce7c7ce03a2158396ee2a7845786e09e2885a9728e4e5",16),
                new BigInteger("d1d27c29fedd92d86c348edd0ccbfac14f746e051ce1d1811df35d61f2ee1c97d4bf2804802f6427187ba8e90a8af44243b4079b03445e602e29fa5193e64fe9",16),
                new BigInteger("8cb2f756bd8941b1d3b770e5ad31ee373b28acda69ff9b6f40fe578b9f1afb85836f9627d37acff73c2779e634bb26011c2c8f7f3361ae2a9ea65ed689e3639a",16));

		
		 byte[] msg1 = StringUtil.toByteArray("087820b569e8fa8d");
		 byte[] msg2 = StringUtil.toByteArray("4653acaf171960b01f52a7be63a3ab21dc368ec43b50d82ec3781e04");
		 byte[] msg3 = StringUtil.toByteArray("d94cd0e08fa404ed89");
		 byte[] msg4 = StringUtil.toByteArray("6cc641b6b61e6f963974dad23a9013284ef1");
		 byte[] msg5 = StringUtil.toByteArray("df5151832b61f4f25891fb4172f328d2eddf8371ffcfdbe997939295f30eca6918017cfda1153bf7a6af87593223");
		 byte[] msg6 = StringUtil.toByteArray("3c3bad893c544a6d520ab022319188c8d504b7a788b850903b85972eaa18552e1134a7ad6098826254ff7ab672b3d8eb3158fac6d4cbaef1");
		 
		 
		
		 byte[] enc1 = StringUtil.toByteArray("026a0485d96aebd96b4382085099b962e6a2bdec3d90c8db625e14372de85e2d5b7baab65c8faf91bb5504fb495afce5c988b3f6a52e20e1d6cbd3566c5cd1f2b8318bb542cc0ea25c4aab9932afa20760eaddec784396a07ea0ef24d4e6f4d37e5052a7a31e146aa480a111bbe926401307e00f410033842b6d82fe5ce4dfae80");
		 byte[] enc2 = StringUtil.toByteArray("024db89c7802989be0783847863084941bf209d761987e38f97cb5f6f1bc88da72a50b73ebaf11c879c4f95df37b850b8f65d7622e25b1b889e80fe80baca2069d6e0e1d829953fc459069de98ea9798b451e557e99abf8fe3d9ccf9096ebbf3e5255d3b4e1c6d2ecadf067a359eea86405acd47d5e165517ccafd47d6dbee4bf5");
		 byte[] enc3 = StringUtil.toByteArray("0239bce681032441528877d6d1c8bb28aa3bc97f1df584563618995797683844ca86664732f4bed7a0aab083aaabfb7238f582e30958c2024e44e57043b97950fd543da977c90cdde5337d618442f99e60d7783ab59ce6dd9d69c47ad1e962bec22d05895cff8d3f64ed5261d92b2678510393484990ba3f7f06818ae6ffce8a3a");
		 byte[] enc4 = StringUtil.toByteArray("02994c62afd76f498ba1fd2cf642857fca81f4373cb08f1cbaee6f025c3b512b42c3e8779113476648039dbe0493f9246292fac28950600e7c0f32edf9c81b9dec45c3bde0cc8d8847590169907b7dc5991ceb29bb0714d613d96df0f12ec5d8d3507c8ee7ae78dd83f216fa61de100363aca48a7e914ae9f42ddfbe943b09d9a0");
		 byte[] enc5 = StringUtil.toByteArray("0162042ff6969592a6167031811a239834ce638abf54fec8b99478122afe2ee67f8c5b18b0339805bfdbc5a4e6720b37c59cfba942464c597ff532a119821545fd2e59b114e61daf71820529f5029cf524954327c34ec5e6f5ba7efcc4de943ab8ad4ed787b1454329f70db798a3a8f4d92f8274e2b2948ade627ce8ee33e43c60");
		 byte[] enc6 = StringUtil.toByteArray("00112051e75d064943bc4478075e43482fd59cee0679de6893eec3a943daa490b9691c93dfc0464b6623b9f3dbd3e70083264f034b374f74164e1a00763725e574744ba0b9db83434f31df96f6e2a26f6d8eba348bd4686c2238ac07c37aac3785d1c7eea2f819fd91491798ed8e9cef5e43b781b0e0276e37c43ff9492d005730");
		 
		 
		ArrayList<byte[]> ciphers = add(enc1, enc2, enc3, enc4, enc5, enc6);
		ArrayList<byte[]> data = add(msg1, msg2, msg3, msg4, msg5, msg6);
		 
		 
		 KeyFactory kf = KeyFactory.getInstance("RSA");
		 PrivateKey prik = kf.generatePrivate(pris);
		 PublicKey pubk = kf.generatePublic(pubs);
		
		 
		 ArrayList<byte[]> results = decrypt(new KeyPair(pubk, prik),  ciphers);
		 
	
		 compare(results, data);
	}
	
	public static void test1027key()
	throws Exception
	{
		RSAPublicKeySpec pubs = new RSAPublicKeySpec(new BigInteger("051240b6cc0004fa48d0134671c078c7c8dec3b3e2f25bc2564467339db38853d06b85eea5b2de353bff42ac2e46bc97fae6ac9618da9537a5c8f553c1e357625991d6108dcd7885fb3a25413f53efcad948cb35cd9b9ae9c1c67626d113d57dde4c5bea76bb5bb7de96c00d07372e9685a6d75cf9d239fa148d70931b5f3fb039",16),
               new BigInteger("010001",16));
		
		RSAPrivateCrtKeySpec pris = new RSAPrivateCrtKeySpec(new BigInteger("051240b6cc0004fa48d0134671c078c7c8dec3b3e2f25bc2564467339db38853d06b85eea5b2de353bff42ac2e46bc97fae6ac9618da9537a5c8f553c1e357625991d6108dcd7885fb3a25413f53efcad948cb35cd9b9ae9c1c67626d113d57dde4c5bea76bb5bb7de96c00d07372e9685a6d75cf9d239fa148d70931b5f3fb039",16),
                new BigInteger("010001",16),
                new BigInteger("0411ffca3b7ca5e9e9be7fe38a85105e353896db05c5796aecd2a725161eb3651c8629a9b862b904d7b0c7b37f8cb5a1c2b54001018a00a1eb2cafe4ee4e9492c348bc2bedab4b9ebbf064e8eff322b9009f8eec653905f40df88a3cdc49d4567f75627d41aca624129b46a0b7c698e5e65f2b7ba102c749a10135b6540d0401",16),
                new BigInteger("027458c19ec1636919e736c9af25d609a51b8f561d19c6bf6943dd1ee1ab8a4a3f232100bd40b88decc6ba235548b6ef792a11c9de823d0a7922c7095b6eba5701",16),
                new BigInteger("0210ee9b33ab61716e27d251bd465f4b35a1a232e2da00901c294bf22350ce490d099f642b5375612db63ba1f20386492bf04d34b3c22bceb909d13441b53b5139",16),
                new BigInteger("39fa028b826e88c1121b750a8b242fa9a35c5b66bdfd1fa637d3cc48a84a4f457a194e7727e49f7bcc6e5a5a412657fc470c7322ebc37416ef458c307a8c0901",16),
                new BigInteger("015d99a84195943979fa9e1be2c3c1b69f432f46fd03e47d5befbbbfd6b1d1371d83efb330a3e020942b2fed115e5d02be24fd92c9019d1cecd6dd4cf1e54cc899",16),
                new BigInteger("01f0b7015170b3f5e42223ba30301c41a6d87cbb70e30cb7d3c67d25473db1f6cbf03e3f9126e3e97968279a865b2c2b426524cfc52a683d31ed30eb984be412ba",16));

		
		 byte[] msg1 = StringUtil.toByteArray("4a86609534ee434a6cbca3f7e962e76d455e3264c19f605f6e5ff6137c65c56d7fb344cd52bc93374f3d166c9f0c6f9c506bad19330972d2");
		 byte[] msg2 = StringUtil.toByteArray("b0adc4f3fe11da59ce992773d9059943c03046497ee9d9f9a06df1166db46d98f58d27ec074c02eee6cbe2449c8b9fc5080c5c3f4433092512ec46aa793743c8");
		 byte[] msg3 = StringUtil.toByteArray("bf6d42e701707b1d0206b0c8b45a1c72641ff12889219a82bdea965b5e79a96b0d0163ed9d578ec9ada20f2fbcf1ea3c4089d83419ba81b0c60f3606da99");
		 byte[] msg4 = StringUtil.toByteArray("fb2ef112f5e766eb94019297934794f7be2f6fc1c58e");
		 byte[] msg5 = StringUtil.toByteArray("28ccd447bb9e85166dabb9e5b7d1adadc4b9d39f204e96d5e440ce9ad928bc1c2284");
		 byte[] msg6 = StringUtil.toByteArray("f22242751ec6b1");
		 
		 
		
		 byte[] enc1 = StringUtil.toByteArray("04cce19614845e094152a3fe18e54e3330c44e5efbc64ae16886cb1869014cc5781b1f8f9e045384d0112a135ca0d12e9c88a8e4063416deaae3844f60d6e96fe155145f4525b9a34431ca3766180f70e15a5e5d8e8b1a516ff870609f13f896935ced188279a58ed13d07114277d75c6568607e0ab092fd803a223e4a8ee0b1a8");
		 byte[] enc2 = StringUtil.toByteArray("0097b698c6165645b303486fbf5a2a4479c0ee85889b541a6f0b858d6b6597b13b854eb4f839af03399a80d79bda6578c841f90d645715b280d37143992dd186c80b949b775cae97370e4ec97443136c6da484e970ffdb1323a20847821d3b18381de13bb49aaea66530c4a4b8271f3eae172cd366e07e6636f1019d2a28aed15e");
		 byte[] enc3 = StringUtil.toByteArray("0301f935e9c47abcb48acbbe09895d9f5971af14839da4ff95417ee453d1fd77319072bb7297e1b55d7561cd9d1bb24c1a9a37c619864308242804879d86ebd001dce5183975e1506989b70e5a83434154d5cbfd6a24787e60eb0c658d2ac193302d1192c6e622d4a12ad4b53923bca246df31c6395e37702c6a78ae081fb9d065");
		 byte[] enc4 = StringUtil.toByteArray("02d110ad30afb727beb691dd0cf17d0af1a1e7fa0cc040ec1a4ba26a42c59d0a796a2e22c8f357ccc98b6519aceb682e945e62cb734614a529407cd452bee3e44fece8423cc19e55548b8b994b849c7ecde4933e76037e1d0ce44275b08710c68e430130b929730ed77e09b015642c5593f04e4ffb9410798102a8e96ffdfe11e4");
		 byte[] enc5 = StringUtil.toByteArray("00dbb8a7439d90efd919a377c54fae8fe11ec58c3b858362e23ad1b8a44310799066b99347aa525691d2adc58d9b06e34f288c170390c5f0e11c0aa3645959f18ee79e8f2be8d7ac5c23d061f18dd74b8c5f2a58fcb5eb0c54f99f01a83247568292536583340948d7a8c97c4acd1e98d1e29dc320e97a260532a8aa7a758a1ec2");
		 byte[] enc6 = StringUtil.toByteArray("00a5ffa4768c8bbecaee2db77e8f2eec99595933545520835e5ba7db9493d3e17cddefe6a5f567624471908db4e2d83a0fbee60608fc84049503b2234a07dc83b27b22847ad8920ff42f674ef79b76280b00233d2b51b8cb2703a9d42bfbc8250c96ec32c051e57f1b4ba528db89c37e4c54e27e6e64ac69635ae887d9541619a9");
		 
		 
		ArrayList<byte[]> ciphers = add(enc1, enc2, enc3, enc4, enc5, enc6);
		ArrayList<byte[]> data = add(msg1, msg2, msg3, msg4, msg5, msg6);
		 
		 
		 KeyFactory kf = KeyFactory.getInstance("RSA");
		 PrivateKey prik = kf.generatePrivate(pris);
		 PublicKey pubk = kf.generatePublic(pubs);
		
		 
		 ArrayList<byte[]> results = decrypt(new KeyPair(pubk, prik), ciphers);
		 
	
		 compare(results, data);
	}
	
	public static void test1028key()
	throws Exception
	{
		RSAPublicKeySpec pubs = new RSAPublicKeySpec(new BigInteger("0aadf3f9c125e5d891f31ac448e993defe580f802b45f9d7f22ba5021e9c47576b5a1e68031ba9db4e6dabe4d96a1d6f3d267268cff408005f118efcadb99888d1c234467166b2a2b849a05a889c060ac0da0c5fae8b55f309ba62e703742fa0326f2d10b011021489ff497770190d895fd39f52293c39efd73a698bdab9f10ed9",16),
              new BigInteger("010001",16));
		
		RSAPrivateCrtKeySpec pris = new RSAPrivateCrtKeySpec(new BigInteger("0aadf3f9c125e5d891f31ac448e993defe580f802b45f9d7f22ba5021e9c47576b5a1e68031ba9db4e6dabe4d96a1d6f3d267268cff408005f118efcadb99888d1c234467166b2a2b849a05a889c060ac0da0c5fae8b55f309ba62e703742fa0326f2d10b011021489ff497770190d895fd39f52293c39efd73a698bdab9f10ed9",16),
                new BigInteger("010001",16),
                new BigInteger("0256eb4cba7067f2d2be540dcdff4582a36b7d31d1c9099bb214b79848466a268f80f58a49ac04c0e3648934a0206c04537c19b236643a6082732144df75fa217588f794682be89168276dc726c5c0cbdb84d31bbf26d0a43af495717f7d528acfee341561f6ff3cae05c578f8470d9682f9c0d072f9f6068b56d5880f682be2c5",16),
                new BigInteger("03b0d3962f6d17549cbfca11294348dcf0e7e39f8c2bc6824f2164b606d687860dae1e632393cfedf513228229069e2f60e4acd7e633a436063f82385f48993707",16),
                new BigInteger("02e4c32e2f517269b7072309f00c0e31365f7ce28b236b82912df239abf39572cf0ed604b02982e53564c52d6a05397de5c052a2fddc141ef7189836346aeb331f",16),
                new BigInteger("01e84b119d25161fa67b00256a5bd9b645d2b232ecb05b015180029a88622adc3f09b3aeacde6161ab7cde22c2ad26e7797df54e072cbd3b2673800b3e4338dbd5",16),
                new BigInteger("eb90aa1a40135b4cea07197cedc8819be1e7cbff2547662116f465a4a9f487ab12f3ba4fef13822265a65297d98b7bded9372e3ffe81a38b3e9600fed055754f",16),
                 new BigInteger("012f7f8138f9404062eb85a42924520b38f5bb886a0196f48bb8dcea60fd92cc027f18e78158a34a5c5d5f860a0f6c04071a7d01312c065062f1eb48b79d1c83cb",16));

		
		 byte[] msg1 = StringUtil.toByteArray("af71a901e3a61d3132f0fc1fdb474f9ea6579257ffc24d164170145b3dbde8");
		 byte[] msg2 = StringUtil.toByteArray("a3b844a08239a8ac41605af17a6cfda4d350136585903a417a79268760519a4b4ac3303ec73f0f87cfb32399");
		 byte[] msg3 = StringUtil.toByteArray("308b0ecbd2c76cb77fc6f70c5edd233fd2f20929d629f026953bb62a8f4a3a314bde195de85b5f816da2aab074d26cb6acddf323ae3b9c678ac3cf12fbdde7");
		 byte[] msg4 = StringUtil.toByteArray("15c5b9ee1185");
		 byte[] msg5 = StringUtil.toByteArray("21026e6800c7fa728fcaaba0d196ae28d7a2ac4ffd8abce794f0985f60c8a6737277365d3fea11db8923a2029a");
		 byte[] msg6 = StringUtil.toByteArray("541e37b68b6c8872b84c02");
		 
		 
		
		 byte[] enc1 = StringUtil.toByteArray("036046a4a47d9ed3ba9a89139c105038eb7492b05a5d68bfd53accff4597f7a68651b47b4a4627d927e485eed7b4566420e8b409879e5d606eae251d22a5df799f7920bfc117b992572a53b1263146bcea03385cc5e853c9a101c8c3e1bda31a519807496c6cb5e5efb408823a352b8fa0661fb664efadd593deb99fff5ed000e5");
		 byte[] enc2 = StringUtil.toByteArray("03d6eb654edce615bc59f455265ed4e5a18223cbb9be4e4069b473804d5de96f54dcaaa603d049c5d94aa1470dfcd2254066b7c7b61ff1f6f6770e3215c51399fd4e34ec5082bc48f089840ad04354ae66dc0f1bd18e461a33cc1258b443a2837a6df26759aa2302334986f87380c9cc9d53be9f99605d2c9a97da7b0915a4a7ad");
		 byte[] enc3 = StringUtil.toByteArray("0770952181649f9f9f07ff626ff3a22c35c462443d905d456a9fd0bff43cac2ca7a9f554e9478b9acc3ac838b02040ffd3e1847de2e4253929f9dd9ee4044325a9b05cabb808b2ee840d34e15d105a3f1f7b27695a1a07a2d73fe08ecaaa3c9c9d4d5a89ff890d54727d7ae40c0ec1a8dd86165d8ee2c6368141016a48b55b6967");
		 byte[] enc4 = StringUtil.toByteArray("0812b76768ebcb642d040258e5f4441a018521bd96687e6c5e899fcd6c17588ff59a82cc8ae03a4b45b31299af1788c329f7dcd285f8cf4ced82606b97612671a45bedca133442144d1617d114f802857f0f9d739751c57a3f9ee400912c61e2e6992be031a43dd48fa6ba14eef7c422b5edc4e7afa04fdd38f402d1c8bb719abf");
		 byte[] enc5 = StringUtil.toByteArray("07b60e14ec954bfd29e60d0047e789f51d57186c63589903306793ced3f68241c743529aba6a6374f92e19e0163efa33697e196f7661dfaaa47aac6bde5e51deb507c72c589a2ca1693d96b1460381249b2cdb9eac44769f2489c5d3d2f99f0ee3c7ee5bf64a5ac79c42bd433f149be8cb59548361640595513c97af7bc2509723");
		 byte[] enc6 = StringUtil.toByteArray("08c36d4dda33423b2ed6830d85f6411ba1dcf470a1fae0ebefee7c089f256cef74cb96ea69c38f60f39abee44129bcb4c92de7f797623b20074e3d9c2899701ed9071e1efa0bdd84d4c3e5130302d8f0240baba4b84a71cc032f2235a5ff0fae277c3e8f9112bef44c9ae20d175fc9a4058bfc930ba31b02e2e4f444483710f24a");
		 
		 
		ArrayList<byte[]> ciphers = add(enc1, enc2, enc3, enc4, enc5, enc6);
		ArrayList<byte[]> data = add(msg1, msg2, msg3, msg4, msg5, msg6);
		 
		 
		 KeyFactory kf = KeyFactory.getInstance("RSA");
		 PrivateKey prik = kf.generatePrivate(pris);
		 PublicKey pubk = kf.generatePublic(pubs);
		
		 
		 ArrayList<byte[]> results = decrypt(new KeyPair(pubk, prik), ciphers);
		 
	
		 compare(results, data);
	}
	
	public static void test1029key()
	throws Exception
	{
		RSAPublicKeySpec pubs = new RSAPublicKeySpec(new BigInteger("12b17f6dad2ecd19ff46dc13f7860f09e0e0cfb677b38a52592305ceaf022c166db90d04ac29e33f7dd12d9faf66e0816bb63ead267cc7d46c17c37be214bca2a22d723a64e44407436b6fc965729aefc2554f376cd5dcea68293780a62bf39d0029485a160bbb9e5dc0972d21a504f52e5ee028aa416332f510b2e9cff5f722af",16),
             new BigInteger("010001",16));
		
		RSAPrivateCrtKeySpec pris = new RSAPrivateCrtKeySpec(new BigInteger("12b17f6dad2ecd19ff46dc13f7860f09e0e0cfb677b38a52592305ceaf022c166db90d04ac29e33f7dd12d9faf66e0816bb63ead267cc7d46c17c37be214bca2a22d723a64e44407436b6fc965729aefc2554f376cd5dcea68293780a62bf39d0029485a160bbb9e5dc0972d21a504f52e5ee028aa416332f510b2e9cff5f722af",16),
                new BigInteger("010001",16),
                new BigInteger("0295eca3560618369559cecd303aa9cfdafc1d9f06959df75ffef929aa896961bcd190dc6997eda7f5963e724d07b4dc11f3065e5ae97d96835112280b9084bb14f2a21ebd4e889d41b9c4132ec1956fcab8bb2fed0575884936522c5ff7d33261904824e7cadee4e0bb372d2457cf78e2bd1286228ff83f10731ce63c90cff3f9",16),
                new BigInteger("04a6ce8b7358dfa69bdcf742617005afb5385f5f3a58a24ef74a22a8c05cb7cc38ebd4cc9d9a9d789a62cd0f60f0cb941d3423c9692efa4fe3adff290c4749a38b",16),
                new BigInteger("0404c9a803371fedb4c5be39f3c00b009e5e08a63be1e40035cdaca5011cc701cf7eebcb99f0ffe17cfd0a4bf7befd2dd536ac946db797fdbc4abe8f29349b91ed",16),
                new BigInteger("03961c8f760aa2bd5154c7aafd77225b3bacd0139ae7b5948ea3311fccd86fb95c75afa767284b9b2de559572f15d8d044c7eb83a1be5fadf2cc377c0d8475294b",16),
                new BigInteger("022197e066742196aabc03fa2feeb4e70b15cb787d617acd31bb75c7bc234ad706f7c48d2182d1f0ff9c228dcf41967b6c0ba6d2c0ad110a1b857831ec245e2cb1",16),
                 new BigInteger("0401c4c0c53d45dbdb5e9d96d0fecf4275df0974bc4a0736b4a74c3269053efb686ace2406e22c9e058ddb4ae540627ae2fdb08261e8e7e4bcbc994daafa305c45",16));

		
		 byte[] msg1 = StringUtil.toByteArray("4046ca8baa3347ca27f49e0d81f9cc1d71be9ba517d4");
		 byte[] msg2 = StringUtil.toByteArray("5cc72c60231df03b3d40f9b57931bc31109f972527f28b19e7480c7288cb3c92b22512214e4be6c914792ddabdf57faa8aa7");
		 byte[] msg3 = StringUtil.toByteArray("b20e651303092f4bccb43070c0f86d23049362ed96642fc5632c27db4a52e3d831f2ab068b23b149879c002f6bf3feee97591112562c");
		 byte[] msg4 = StringUtil.toByteArray("684e3038c5c041f7");
		 byte[] msg5 = StringUtil.toByteArray("32488cb262d041d6e4dd35f987bf3ca696db1f06ac29a44693");
		 byte[] msg6 = StringUtil.toByteArray("50ba14be8462720279c306ba");
		 
		 
		
		 byte[] enc1 = StringUtil.toByteArray("0630eebcd2856c24f798806e41f9e67345eda9ceda386acc9facaea1eeed06ace583709718d9d169fadf414d5c76f92996833ef305b75b1e4b95f662a20faedc3bae0c4827a8bf8a88edbd57ec203a27a841f02e43a615bab1a8cac0701de34debdef62a088089b55ec36ea7522fd3ec8d06b6a073e6df833153bc0aefd93bd1a3");
		 byte[] enc2 = StringUtil.toByteArray("0ebc37376173a4fd2f89cc55c2ca62b26b11d51c3c7ce49e8845f74e7607317c436bc8d23b9667dfeb9d087234b47bc6837175ae5c0559f6b81d7d22416d3e50f4ac533d8f0812f2db9e791fe9c775ac8b6ad0f535ad9ceb23a4a02014c58ab3f8d3161499a260f39348e714ae2a1d3443208fd8b722ccfdfb393e98011f99e63f");
		 byte[] enc3 = StringUtil.toByteArray("0a98bf1093619394436cf68d8f38e2f158fde8ea54f3435f239b8d06b8321844202476aeed96009492480ce3a8d705498c4c8c68f01501dc81db608f60087350c8c3b0bd2e9ef6a81458b7c801b89f2e4fe99d4900ba6a4b5e5a96d865dc676c7755928794130d6280a8160a190f2df3ea7cf9aa0271d88e9e6905ecf1c5152d65");
		 byte[] enc4 = StringUtil.toByteArray("008e7a67cacfb5c4e24bec7dee149117f19598ce8c45808fef88c608ff9cd6e695263b9a3c0ad4b8ba4c95238e96a8422b8535629c8d5382374479ad13fa39974b242f9a759eeaf9c83ad5a8ca18940a0162ba755876df263f4bd50c6525c56090267c1f0e09ce0899a0cf359e88120abd9bf893445b3cae77d3607359ae9a52f8");
		 byte[] enc5 = StringUtil.toByteArray("00003474416c7b68bdf961c385737944d7f1f40cb395343c693cc0b4fe63b31fedf1eaeeac9ccc0678b31dc32e0977489514c4f09085f6298a9653f01aea4045ff582ee887be26ae575b73eef7f3774921e375a3d19adda0ca31aa1849887c1f42cac9677f7a2f4e923f6e5a868b38c084ef187594dc9f7f048fea2e02955384ab");
		 byte[] enc6 = StringUtil.toByteArray("0a026dda5fc8785f7bd9bf75327b63e85e2c0fdee5dadb65ebdcac9ae1de95c92c672ab433aa7a8e69ce6a6d8897fac4ac4a54de841ae5e5bbce7687879d79634cea7a30684065c714d52409b928256bbf53eabcd5231eb7259504537399bd29164b726d33a46da701360a4168a091ccab72d44a62fed246c0ffea5b1348ab5470");
		 
		 
		ArrayList<byte[]> ciphers = add(enc1, enc2, enc3, enc4, enc5, enc6);
		ArrayList<byte[]> data = add(msg1, msg2, msg3, msg4, msg5, msg6);
		 
		 
		 KeyFactory kf = KeyFactory.getInstance("RSA");
		 PrivateKey prik = kf.generatePrivate(pris);
		 PublicKey pubk = kf.generatePublic(pubs);
		
		 
		 ArrayList<byte[]> results = decrypt(new KeyPair(pubk, prik), ciphers);
		 
	
		 compare(results, data);
	}
	
	public static void test1030key()
	throws Exception
	{
		RSAPublicKeySpec pubs = new RSAPublicKeySpec(new BigInteger("311179f0bcfc9b9d3ca315d00ef30d7bdd3a2cfae9911bfedcb948b3a4782d0732b6ab44aa4bf03741a644dc01bec3e69b01a033e675d8acd7c4925c6b1aec3119051dfd89762d215d45475ffcb59f908148623f37177156f6ae86dd7a7c5f43dc1e1f908254058a284a5f06c0021793a87f1ac5feff7dcaee69c5e51a3789e373",16),
            new BigInteger("010001",16));
		
		RSAPrivateCrtKeySpec pris = new RSAPrivateCrtKeySpec(new BigInteger("311179f0bcfc9b9d3ca315d00ef30d7bdd3a2cfae9911bfedcb948b3a4782d0732b6ab44aa4bf03741a644dc01bec3e69b01a033e675d8acd7c4925c6b1aec3119051dfd89762d215d45475ffcb59f908148623f37177156f6ae86dd7a7c5f43dc1e1f908254058a284a5f06c0021793a87f1ac5feff7dcaee69c5e51a3789e373",16),
                new BigInteger("010001",16),
                new BigInteger("070cfcff2feb8276e27432c45dfee48f49b7917d6530e1f0ca3460f32e0276174487c56e22a45d2500d7775495219d7d165a9cf3bd92c32af9a98d8dc9cc296800adc94a0a54fb40f34291bf84ee8ea12b6f109359c6d3542a50f9c767f5cfff05a681c2e656fb77caaadb4be9468d8abcd4df98f58e86d2053fa1349f748e21b1",16),
                new BigInteger("0749262c111cd470ec2566e6b3732fc09329469aa19071d3b9c01906514c6f1d26baa14beab0971c8b7e611a4f79009d6fea776928ca25285b0de3643d1a3f8c71",16),
                new BigInteger("06bc1e50e96c02bf636e9eea8b899bbebf7651de77dd474c3e9bc23bad8182b61904c7d97dfbebfb1e00108878b6e67e415391d67942c2b2bf9b4435f88b0cb023",16),
                new BigInteger("03bc7ea7f0aab143abc6ce8b97118636a30172e4cfe02c8fa0dda3b7baaf90f8092982985525f488bdfcb4bd726e22639ac64a3092ab7ffcbf1d5334cfa50b5bf1",16),
                new BigInteger("0262a6aa29c2a3c67dc5346c06381afd987aa3cc93cfbfecf54fdd9f9d787d7f59a523d398979da137a2f6381fe94801f7c94da21518dc34cb40870c4697994ad9",16),
                 new BigInteger("649d4c17b6ee1721e772d0389a559c3d3cdf9550d457c46b037b74641b1d52166af8a213c8396206cdfba4422f18d6f61dbcb5d214c971bf482aeb976a7370c2",16));

		
		 byte[] msg1 = StringUtil.toByteArray("47aae909");
		 byte[] msg2 = StringUtil.toByteArray("1d9b2e2223d9bc13bfb9f162ce735db48ba7c68f6822a0a1a7b6ae165834e7");
		 byte[] msg3 = StringUtil.toByteArray("d976fc");
		 byte[] msg4 = StringUtil.toByteArray("d4738623df223aa43843df8467534c41d013e0c803c624e263666b239bde40a5f29aeb8de79e3daa61dd0370f49bd4b013834b98212aef6b1c5ee373b3cb");
		 byte[] msg5 = StringUtil.toByteArray("bb47231ca5ea1d3ad46c99345d9a8a61");
		 byte[] msg6 = StringUtil.toByteArray("2184827095d35c3f86f600e8e59754013296");
		 
		 
		
		 byte[] enc1 = StringUtil.toByteArray("1688e4ce7794bba6cb7014169ecd559cede2a30b56a52b68d9fe18cf1973ef97b2a03153951c755f6294aa49adbdb55845ab6875fb3986c93ecf927962840d282f9e54ce8b690f7c0cb8bbd73440d9571d1b16cd9260f9eab4783cc482e5223dc60973871783ec27b0ae0fd47732cbc286a173fc92b00fb4ba6824647cd93c85c1");
		 byte[] enc2 = StringUtil.toByteArray("1052ed397b2e01e1d0ee1c50bf24363f95e504f4a03434a08fd822574ed6b9736edbb5f390db10321479a8a139350e2bd4977c3778ef331f3e78ae118b268451f20a2f01d471f5d53c566937171b2dbc2d4bde459a5799f0372d6574239b2323d245d0bb81c286b63c89a361017337e4902f88a467f4c7f244bfd5ab46437ff3b6");
		 byte[] enc3 = StringUtil.toByteArray("2155cd843ff24a4ee8badb7694260028a490813ba8b369a4cbf106ec148e5298707f5965be7d101c1049ea8584c24cd63455ad9c104d686282d3fb803a4c11c1c2e9b91c7178801d1b6640f003f5728df007b8a4ccc92bce05e41a27278d7c85018c52414313a5077789001d4f01910b72aad05d220aa14a58733a7489bc54556b");
		 byte[] enc4 = StringUtil.toByteArray("0ab14c373aeb7d4328d0aaad8c094d88b9eb098b95f21054a29082522be7c27a312878b637917e3d819e6c3c568db5d843802b06d51d9e98a2be0bf40c031423b00edfbff8320efb9171bd2044653a4cb9c5122f6c65e83cda2ec3c126027a9c1a56ba874d0fea23f380b82cf240b8cf540004758c4c77d934157a74f3fc12bfac");
		 byte[] enc5 = StringUtil.toByteArray("028387a318277434798b4d97f460068df5298faba5041ba11761a1cb7316b24184114ec500257e2589ed3b607a1ebbe97a6cc2e02bf1b681f42312a33b7a77d8e7855c4a6de03e3c04643f786b91a264a0d6805e2cea91e68177eb7a64d9255e4f27e713b7ccec00dc200ebd21c2ea2bb890feae4942df941dc3f97890ed347478");
		 byte[] enc6 = StringUtil.toByteArray("14c678a94ad60525ef39e959b2f3ba5c097a94ff912b67dbace80535c187abd47d075420b1872152bba08f7fc31f313bbf9273c912fc4c0149a9b0cfb79807e346eb332069611bec0ff9bcd168f1f7c33e77313cea454b94e2549eecf002e2acf7f6f2d2845d4fe0aab2e5a92ddf68c480ae11247935d1f62574842216ae674115");
		 
		 
		ArrayList<byte[]> ciphers = add(enc1, enc2, enc3, enc4, enc5, enc6);
		ArrayList<byte[]> data = add(msg1, msg2, msg3, msg4, msg5, msg6);
		 
		 
		 KeyFactory kf = KeyFactory.getInstance("RSA");
		 PrivateKey prik = kf.generatePrivate(pris);
		 PublicKey pubk = kf.generatePublic(pubs);
		
		 
		 ArrayList<byte[]> results = decrypt(new KeyPair(pubk, prik), ciphers);
		 
	
		 compare(results, data);
	}
	
	public static void test1031key()
	throws Exception
	{
		RSAPublicKeySpec pubs = new RSAPublicKeySpec(new BigInteger("5bdf0e30d321dda5147f882408fa69195480df8f80d3f6e8bf5818504f36427ca9b1f5540b9c65a8f6974cf8447a244d9280201bb49fcbbe6378d1944cd227e230f96e3d10f819dcef276c64a00b2a4b6701e7d01de5fabde3b1e9a0df82f4631359cd22669647fbb1717246134ed7b497cfffbdc42b59c73a96ed90166212dff7",16),
            new BigInteger("010001",16));
		
		RSAPrivateCrtKeySpec pris = new RSAPrivateCrtKeySpec(new BigInteger("5bdf0e30d321dda5147f882408fa69195480df8f80d3f6e8bf5818504f36427ca9b1f5540b9c65a8f6974cf8447a244d9280201bb49fcbbe6378d1944cd227e230f96e3d10f819dcef276c64a00b2a4b6701e7d01de5fabde3b1e9a0df82f4631359cd22669647fbb1717246134ed7b497cfffbdc42b59c73a96ed90166212dff7",16),
                new BigInteger("010001",16),
                new BigInteger("0f7d1e9e5aaa25fd13e4a0663ae144e0d15f5cd18bcdb09df2cc7e64e3c5e915ad62645304161d098c715bb7ab8bd01d07eaf3fed7c7ed08af2a8a62ef44ab16b320e14af72a48f96afe262a0ae4cf65e635e910790cd4ee5cea768a4b2639f7e6f677b3f0bb6be32b75747d8909036f0264f58d401cdba131716157a75ecf6331",16),
                new BigInteger("0a02ef8448d9fad8bbd0d004c8c2aa9751ef9721c1b0d03236a54b0df947cbaed5a255ee9e8e20d491ea1723fe094704a9762e88afd16ebb5994412ca966dc4f9f",16),
                new BigInteger("092d362e7ed3a0bfd9e9fd0e6c0301b6df29159cf50cc83b9b0cf4d6eea71a61e002b46e0ae9f2de62d25b5d7452d498b81c9ac6fc58593d4c3fb4f5d72dfbb0a9",16),
                new BigInteger("07c71410af103962db367404e37ae850baa4e9c29dd92145815294a67c7d1c6ded263aa030a9b633ae50303e14035d1af014123eba687820308d8ebc85b6957d7d",16),
                new BigInteger("ae2c75380c02c016ad05891b3301de881f28ae1171182b6b2c83bea7c515eca9ca298c7b1cab5817a597068fc85060de4da8a016378aae43c7f967bcc37904b9",16),
                new BigInteger("0598d1059e3ada4f6320752c09d805ff7d1f1ae0d017aeeee9cefa0d7dd7ff775e44b578322f6405d6211da19519666aa87fdc4cd8c88f6b6e3d67e961dcbba3d0",16));

		
		 byte[] msg1 = StringUtil.toByteArray("050b755e5e6880f7b9e9d692a74c37aae449b31bfea6deff83747a897f6c2c825bb1adbf850a3c96994b5de5b33cbc7d4a17913a7967");
		 byte[] msg2 = StringUtil.toByteArray("4eb68dcd93ca9b19df111bd43608f557026fe4aa1d5cfac227a3eb5ab9548c18a06dded23f81825986b2fcd71109ecef7eff88873f075c2aa0c469f69c92bc");
		 byte[] msg3 = StringUtil.toByteArray("8604ac56328c1ab5ad917861");
		 byte[] msg4 = StringUtil.toByteArray("fdda5fbf6ec361a9d9a4ac68af216a0686f438b1e0e5c36b955f74e107f39c0dddcc");
		 byte[] msg5 = StringUtil.toByteArray("4a5f4914bee25de3c69341de07");
		 byte[] msg6 = StringUtil.toByteArray("8e07d66f7b880a72563abcd3f35092bc33409fb7f88f2472be");
		 
		 
		
		 byte[] enc1 = StringUtil.toByteArray("09b3683d8a2eb0fb295b62ed1fb9290b714457b7825319f4647872af889b30409472020ad12912bf19b11d4819f49614824ffd84d09c0a17e7d17309d12919790410aa2995699f6a86dbe3242b5acc23af45691080d6b1ae810fb3e3057087f0970092ce00be9562ff4053b6262ce0caa93e13723d2e3a5ba075d45f0d61b54b61");
		 byte[] enc2 = StringUtil.toByteArray("2ecf15c97c5a15b1476ae986b371b57a24284f4a162a8d0c8182e7905e792256f1812ba5f83f1f7a130e42dcc02232844edc14a31a68ee97ae564a383a3411656424c5f62ddb646093c367be1fcda426cf00a06d8acb7e57776fbbd855ac3df506fc16b1d7c3f2110f3d8068e91e186363831c8409680d8da9ecd8cf1fa20ee39d");
		 byte[] enc3 = StringUtil.toByteArray("4bc89130a5b2dabb7c2fcf90eb5d0eaf9e681b7146a38f3173a3d9cfec52ea9e0a41932e648a9d69344c50da763f51a03c95762131e8052254dcd2248cba40fd31667786ce05a2b7b531ac9dac9ed584a59b677c1a8aed8c5d15d68c05569e2be780bf7db638fd2bfd2a85ab276860f3777338fca989ffd743d13ee08e0ca9893f");
		 byte[] enc4 = StringUtil.toByteArray("2e456847d8fc36ff0147d6993594b9397227d577752c79d0f904fcb039d4d812fea605a7b574dd82ca786f93752348438ee9f5b5454985d5f0e1699e3e7ad175a32e15f03deb042ab9fe1dd9db1bb86f8c089ccb45e7ef0c5ee7ca9b7290ca6b15bed47039788a8a93ff83e0e8d6244c71006362deef69b6f416fb3c684383fbd0");
		 byte[] enc5 = StringUtil.toByteArray("1fb9356fd5c4b1796db2ebf7d0d393cc810adf6145defc2fce714f79d93800d5e2ac211ea8bbecca4b654b94c3b18b30dd576ce34dc95436ef57a09415645923359a5d7b4171ef22c24670f1b229d3603e91f76671b7df97e7317c97734476d5f3d17d21cf82b5ba9f83df2e588d36984fd1b584468bd23b2e875f32f68953f7b2");
		 byte[] enc6 = StringUtil.toByteArray("3afd9c6600147b21798d818c655a0f4c9212db26d0b0dfdc2a7594ccb3d22f5bf1d7c3e112cd73fc7d509c7a8bafdd3c274d1399009f9609ec4be6477e453f075aa33db382870c1c3409aef392d7386ae3a696b99a94b4da0589447e955d16c98b17602a59bd736279fcd8fb280c4462d590bfa9bf13fed570eafde97330a2c210");
		 
		 
		ArrayList<byte[]> ciphers = add(enc1, enc2, enc3, enc4, enc5, enc6);
		ArrayList<byte[]> data = add(msg1, msg2, msg3, msg4, msg5, msg6);
		 
		 
		 KeyFactory kf = KeyFactory.getInstance("RSA");
		 PrivateKey prik = kf.generatePrivate(pris);
		 PublicKey pubk = kf.generatePublic(pubs);
		
		 
		 ArrayList<byte[]> results = decrypt(new KeyPair(pubk, prik), ciphers);
		 
	
		 compare(results, data);
		 
		 
	}
	
	
	public static void test1536key()
	throws Exception
	{
		RSAPublicKeySpec pubs = new RSAPublicKeySpec(new BigInteger("cf2cd41e34ca3a728ea5cb8aff64c36d27bdef5364e336fd68d3123c5a196a8c287013e853d5156d58d151954520fb4f6d7b17abb6817765909c576119659d902b1906ed8a2b10c155c24d124528dab9eeae379beac66e4a411786dcb8fd0062ebc030de1219a04c2a8c1b7dd3131e4d6b6caee2e31a5ed41ac1509b2ef1ee2ab18364be568ca941c25ecc84ff9d643b5ec1aaae102a20d73f479b780fd6da91075212d9eac03a0674d899eba2e431f4c44b615b6ba2232bd4b33baed73d625d",16),
           new BigInteger("010001",16));
		
		RSAPrivateCrtKeySpec pris = new RSAPrivateCrtKeySpec(new BigInteger("cf2cd41e34ca3a728ea5cb8aff64c36d27bdef5364e336fd68d3123c5a196a8c287013e853d5156d58d151954520fb4f6d7b17abb6817765909c576119659d902b1906ed8a2b10c155c24d124528dab9eeae379beac66e4a411786dcb8fd0062ebc030de1219a04c2a8c1b7dd3131e4d6b6caee2e31a5ed41ac1509b2ef1ee2ab18364be568ca941c25ecc84ff9d643b5ec1aaae102a20d73f479b780fd6da91075212d9eac03a0674d899eba2e431f4c44b615b6ba2232bd4b33baed73d625d",16),
                new BigInteger("010001",16),
                new BigInteger("198c141e23715a92bccf6a119a5bc11389468d2811f548d727e17b4ab0eb986d6f211efb53b71f7ccbea87ee69c75ee615008c5332deb52bf390abdfbfe37d7205368159b2638c1de326e21d22251f0fb5848b3bf15005d2a74330f0afe916ee62ccc1344d1d83a709e60676273840f7f377424a5e0a4da75f01b31ff76819cf9cbfdd215243c3917c03ef38199312e567b3bf7aed3ab457f371ef8a1423f45b68c6e282ec111bba2833b987fd69fad83bc1b8c613c5e1ea16c11ed125ea7ec1",16),
               new BigInteger("fc8d6c04bec4eb9a8192ca7900cbe536e2e8b519decf33b2459798c6909df4f176db7d23190fc72b8865a718af895f1bcd9145298027423b605e70a47cf58390a8c3e88fc8c48e8b32e3da210dfbe3e881ea5674b6a348c21e93f9e55ea65efd",16),
                new BigInteger("d200d45e788aacea606a401d0460f87dd5c1027e12dc1a0d7586e8939d9cf789b40f51ac0442961de7d21cc21e05c83155c1f2aa9193387cfdf956cb48d153ba270406f9bbba537d4987d9e2f9942d7a14cbfffea74fecdda928d23e259f5ee1",16),
                new BigInteger("db16802f79a2f0d45f358d69fd33e44b81fae828622e93a54253e997d01b0743759da0e812b4aa4e6c8beab2328d5431955a418a67ff26a8c5c807a5da354e05ef31cc8cf758f463732950b03e265726fb94e39d6a572a26244ab08db75752ad",16),
                new BigInteger("a0a317cfe7df1423f87a6dee8451f4e2b4a67e5497f29b4f1e4e830b9fadd9401167026f5596e5a39c97817e0f5f16e27e19ec9902e01d7ea6fb9aa3c760afee1e381b69de6ac9c07585a06ad9c4ba00bf75c8ad2fa898a479e80ae294fed2a1",16),
                new BigInteger("0b21f335c353342eb44c3aa24445780c2d655b940174cae38c7c8a4e6493c0ba9fd303748267b083b9a7a6cb61e42db362b8c9896db7064e02ad5ae61587da15b4649c90594909feb37dbcb654beb7268ec801e5a8b4aa3911bebd88542f05be",16));

		
		 byte[] msg1 = StringUtil.toByteArray("f735fd55ba92592c3b52b8f9c4f69aaa1cbef8fe88add095595412467f9cf4ec0b896c59eda16210e7549c8abb10cdbc21a12ec9b6b5b8fd2f10399eb6");
		 byte[] msg2 = StringUtil.toByteArray("81b906605015a63aabe42ddf11e1978912f5404c7474b26dce3ed482bf961ecc818bf420c54659");
		 byte[] msg3 = StringUtil.toByteArray("fd326429df9b890e09b54b18b8f34f1e24");
		 byte[] msg4 = StringUtil.toByteArray("f1459b5f0c92f01a0f723a2e5662484d8f8c0a20fc29dad6acd43bb5f3effdf4e1b63e07fdfe6628d0d74ca19bf2d69e4a0abf86d293925a796772f8088e");
		 byte[] msg5 = StringUtil.toByteArray("53e6e8c729d6f9c319dd317e74b0db8e4ccca25f3c8305746e137ac63a63ef3739e7b595abb96e8d55e54f7bd41ab433378ffb911d");
		 byte[] msg6 = StringUtil.toByteArray("b6b28ea2198d0c1008bc64");
		 
		 
		
		 byte[] enc1 = StringUtil.toByteArray("267bcd118acab1fc8ba81c85d73003cb8610fa55c1d97da8d48a7c7f06896a4db751aa284255b9d36ad65f37653d829f1b37f97b8001942545b2fc2c55a7376ca7a1be4b1760c8e05a33e5aa2526b8d98e317088e7834c755b2a59b12631a182c05d5d43ab1779264f8456f515ce57dfdf512d5493dab7b7338dc4b7d78db9c091ac3baf537a69fc7f549d979f0eff9a94fda4169bd4d1d19a69c99e33c3b55490d501b39b1edae118ff6793a153261584d3a5f39f6e682e3d17c8cd1261fa72");
		 byte[] enc2 = StringUtil.toByteArray("93ac9f0671ec29acbb444effc1a5741351d60fdb0e393fbf754acf0de49761a14841df7772e9bc82773966a1584c4d72baea00118f83f35cca6e537cbd4d811f5583b29783d8a6d94cd31be70d6f526c10ff09c6fa7ce069795a3fcd0511fd5fcb564bcc80ea9c78f38b80012539d8a4ddf6fe81e9cddb7f50dbbbbcc7e5d86097ccf4ec49189fb8bf318be6d5a0715d516b49af191258cd32dc833ce6eb4673c03a19bbace88cc54895f636cc0c1ec89096d11ce235a265ca1764232a689ae8");
		 byte[] enc3 = StringUtil.toByteArray("81ebdd95054b0c822ef9ad7693f5a87adfb4b4c4ce70df2df84ed49c04da58ba5fc20a19e1a6e8b7a3900b22796dc4e869ee6b42792d15a8eceb56c09c69914e813cea8f6931e4b8ed6f421af298d595c97f4789c7caa612c7ef360984c21b93edc5401068b5af4c78a8771b984d53b8ea8adf2f6a7d4a0ba76c75e1dd9f658f20ded4a46071d46d7791b56803d8fea7f0b0f8e41ae3f09383a6f9585fe7753eaaffd2bf94563108beecc207bbb535f5fcc705f0dde9f708c62f49a9c90371d3");
		 byte[] enc4 = StringUtil.toByteArray("bcc35f94cde66cb1136625d625b94432a35b22f3d2fa11a613ff0fca5bd57f87b902ccdc1cd0aebcb0715ee869d1d1fe395f6793003f5eca465059c88660d446ff5f0818552022557e38c08a67ead991262254f10682975ec56397768537f4977af6d5f6aaceb7fb25dec5937230231fd8978af49119a29f29e424ab8272b47562792d5c94f774b8829d0b0d9f1a8c9eddf37574d5fa248eefa9c5271fc5ec2579c81bdd61b410fa61fe36e424221c113addb275664c801d34ca8c6351e4a858");
		 byte[] enc5 = StringUtil.toByteArray("232afbc927fa08c2f6a27b87d4a5cb09c07dc26fae73d73a90558839f4fd66d281b87ec734bce237ba166698ed829106a7de6942cd6cdce78fed8d2e4d81428e66490d036264cef92af941d3e35055fe3981e14d29cbb9a4f67473063baec79a1179f5a17c9c1832f2838fd7d5e59bb9659d56dce8a019edef1bb3accc697cc6cc7a778f60a064c7f6f5d529c6210262e003de583e81e3167b89971fb8c0e15d44fffef89b53d8d64dd797d159b56d2b08ea5307ea12c241bd58d4ee278a1f2e");
		 byte[] enc6 = StringUtil.toByteArray("438cc7dc08a68da249e42505f8573ba60e2c2773d5b290f4cf9dff718e842081c383e67024a0f29594ea987b9d25e4b738f285970d195abb3a8c8054e3d79d6b9c9a8327ba596f1259e27126674766907d8d582ff3a8476154929adb1e6d1235b2ccb4ec8f663ba9cc670a92bebd853c8dbf69c6436d016f61add836e94732450434207f9fd4c43dec2a12a958efa01efe2669899b5e604c255c55fb7166de5589e369597bb09168c06dd5db177e06a1740eb2d5c82faeca6d92fcee9931ba9f");
		 
		 
		ArrayList<byte[]> ciphers = add(enc1, enc2, enc3, enc4, enc5, enc6);
		ArrayList<byte[]> data = add(msg1, msg2, msg3, msg4, msg5, msg6);
		 
		 
		 KeyFactory kf = KeyFactory.getInstance("RSA");
		 PrivateKey prik = kf.generatePrivate(pris);
		 PublicKey pubk = kf.generatePublic(pubs);
		
		 
		 ArrayList<byte[]> results = decrypt(new KeyPair(pubk, prik),  ciphers);
		 
	
		 compare(results, data);
	}
	
	public static void test2048key()
	throws Exception
	{
		RSAPublicKeySpec pubs = new RSAPublicKeySpec(new BigInteger("ae45ed5601cec6b8cc05f803935c674ddbe0d75c4c09fd7951fc6b0caec313a8df39970c518bffba5ed68f3f0d7f22a4029d413f1ae07e4ebe9e4177ce23e7f5404b569e4ee1bdcf3c1fb03ef113802d4f855eb9b5134b5a7c8085adcae6fa2fa1417ec3763be171b0c62b760ede23c12ad92b980884c641f5a8fac26bdad4a03381a22fe1b754885094c82506d4019a535a286afeb271bb9ba592de18dcf600c2aeeae56e02f7cf79fc14cf3bdc7cd84febbbf950ca90304b2219a7aa063aefa2c3c1980e560cd64afe779585b6107657b957857efde6010988ab7de417fc88d8f384c4e6e72c3f943e0c31c0c4a5cc36f879d8a3ac9d7d59860eaada6b83bb",16),
           new BigInteger("010001",16));
		
		RSAPrivateCrtKeySpec pris = new RSAPrivateCrtKeySpec(new BigInteger("ae45ed5601cec6b8cc05f803935c674ddbe0d75c4c09fd7951fc6b0caec313a8df39970c518bffba5ed68f3f0d7f22a4029d413f1ae07e4ebe9e4177ce23e7f5404b569e4ee1bdcf3c1fb03ef113802d4f855eb9b5134b5a7c8085adcae6fa2fa1417ec3763be171b0c62b760ede23c12ad92b980884c641f5a8fac26bdad4a03381a22fe1b754885094c82506d4019a535a286afeb271bb9ba592de18dcf600c2aeeae56e02f7cf79fc14cf3bdc7cd84febbbf950ca90304b2219a7aa063aefa2c3c1980e560cd64afe779585b6107657b957857efde6010988ab7de417fc88d8f384c4e6e72c3f943e0c31c0c4a5cc36f879d8a3ac9d7d59860eaada6b83bb",16),
                new BigInteger("010001",16),
                new BigInteger("056b04216fe5f354ac77250a4b6b0c8525a85c59b0bd80c56450a22d5f438e596a333aa875e291dd43f48cb88b9d5fc0d499f9fcd1c397f9afc070cd9e398c8d19e61db7c7410a6b2675dfbf5d345b804d201add502d5ce2dfcb091ce9997bbebe57306f383e4d588103f036f7e85d1934d152a323e4a8db451d6f4a5b1b0f102cc150e02feee2b88dea4ad4c1baccb24d84072d14e1d24a6771f7408ee30564fb86d4393a34bcf0b788501d193303f13a2284b001f0f649eaf79328d4ac5c430ab4414920a9460ed1b7bc40ec653e876d09abc509ae45b525190116a0c26101848298509c1c3bf3a483e7274054e15e97075036e989f60932807b5257751e79",16),
               new BigInteger("ecf5aecd1e5515fffacbd75a2816c6ebf49018cdfb4638e185d66a7396b6f8090f8018c7fd95cc34b857dc17f0cc6516bb1346ab4d582cadad7b4103352387b70338d084047c9d9539b6496204b3dd6ea442499207bec01f964287ff6336c3984658336846f56e46861881c10233d2176bf15a5e96ddc780bc868aa77d3ce769",16),
                new BigInteger("bc46c464fc6ac4ca783b0eb08a3c841b772f7e9b2f28babd588ae885e1a0c61e4858a0fb25ac299990f35be85164c259ba1175cdd7192707135184992b6c29b746dd0d2cabe142835f7d148cc161524b4a09946d48b828473f1ce76b6cb6886c345c03e05f41d51b5c3a90a3f24073c7d74a4fe25d9cf21c75960f3fc3863183",16),
               new BigInteger("c73564571d00fb15d08a3de9957a50915d7126e9442dacf42bc82e862e5673ff6a008ed4d2e374617df89f17a160b43b7fda9cb6b6b74218609815f7d45ca263c159aa32d272d127faf4bc8ca2d77378e8aeb19b0ad7da3cb3de0ae7314980f62b6d4b0a875d1df03c1bae39ccd833ef6cd7e2d9528bf084d1f969e794e9f6c1",16),
               new BigInteger("2658b37f6df9c1030be1db68117fa9d87e39ea2b693b7e6d3a2f70947413eec6142e18fb8dfcb6ac545d7c86a0ad48f8457170f0efb26bc48126c53efd1d16920198dc2a1107dc282db6a80cd3062360ba3fa13f70e4312ff1a6cd6b8fc4cd9c5c3db17c6d6a57212f73ae29f619327bad59b153858585ba4e28b60a62a45e49",16),
               new BigInteger("6f38526b3925085534ef3e415a836ede8b86158a2c7cbfeccb0bd834304fec683ba8d4f479c433d43416e63269623cea100776d85aff401d3fff610ee65411ce3b1363d63a9709eede42647cea561493d54570a879c18682cd97710b96205ec31117d73b5f36223fadd6e8ba90dd7c0ee61d44e163251e20c7f66eb305117cb8",16));

		
		 byte[] msg1 = StringUtil.toByteArray("8bba6bf82a6c0f86d5f1756e97956870b08953b06b4eb205bc1694ee");
		 byte[] msg2 = StringUtil.toByteArray("e6ad181f053b58a904f2457510373e57");
		 byte[] msg3 = StringUtil.toByteArray("510a2cf60e866fa2340553c94ea39fbc256311e83e94454b4124");
		 byte[] msg4 = StringUtil.toByteArray("bcdd190da3b7d300df9a06e22caae2a75f10c91ff667b7c16bde8b53064a2649a94045c9");
		 byte[] msg5 = StringUtil.toByteArray("a7dd6c7dc24b46f9dd5f1e91ada4c3b3df947e877232a9");
		 byte[] msg6 = StringUtil.toByteArray("eaf1a73a1b0c4609537de69cd9228bbcfb9a8ca8c6c3efaf056fe4a7f4634ed00b7c39ec6922d7b8ea2c04ebac");
		 
		 
		
		 byte[] enc1 = StringUtil.toByteArray("53ea5dc08cd260fb3b858567287fa91552c30b2febfba213f0ae87702d068d19bab07fe574523dfb42139d68c3c5afeee0bfe4cb7969cbf382b804d6e61396144e2d0e60741f8993c3014b58b9b1957a8babcd23af854f4c356fb1662aa72bfcc7e586559dc4280d160c126785a723ebeebeff71f11594440aaef87d10793a8774a239d4a04c87fe1467b9daf85208ec6c7255794a96cc29142f9a8bd418e3c1fd67344b0cd0829df3b2bec60253196293c6b34d3f75d32f213dd45c6273d505adf4cced1057cb758fc26aeefa441255ed4e64c199ee075e7f16646182fdb464739b68ab5daff0e63e9552016824f054bf4d3c8c90a97bb6b6553284eb429fcc");
		 byte[] enc2 = StringUtil.toByteArray("a2b1a430a9d657e2fa1c2bb5ed43ffb25c05a308fe9093c01031795f5874400110828ae58fb9b581ce9dddd3e549ae04a0985459bde6c626594e7b05dc4278b2a1465c1368408823c85e96dc66c3a30983c639664fc4569a37fe21e5a195b5776eed2df8d8d361af686e750229bbd663f161868a50615e0c337bec0ca35fec0bb19c36eb2e0bbcc0582fa1d93aacdb061063f59f2ce1ee43605e5d89eca183d2acdfe9f81011022ad3b43a3dd417dac94b4e11ea81b192966e966b182082e71964607b4f8002f36299844a11f2ae0faeac2eae70f8f4f98088acdcd0ac556e9fccc511521908fad26f04c64201450305778758b0538bf8b5bb144a828e629795");
		 byte[] enc3 = StringUtil.toByteArray("9886c3e6764a8b9a84e84148ebd8c3b1aa8050381a78f668714c16d9cfd2a6edc56979c535d9dee3b44b85c18be8928992371711472216d95dda98d2ee8347c9b14dffdff84aa48d25ac06f7d7e65398ac967b1ce90925f67dce049b7f812db0742997a74d44fe81dbe0e7a3feaf2e5c40af888d550ddbbe3bc20657a29543f8fc2913b9bd1a61b2ab2256ec409bbd7dc0d17717ea25c43f42ed27df8738bf4afc6766ff7aff0859555ee283920f4c8a63c4a7340cbafddc339ecdb4b0515002f96c932b5b79167af699c0ad3fccfdf0f44e85a70262bf2e18fe34b850589975e867ff969d48eabf212271546cdc05a69ecb526e52870c836f307bd798780ede");
		 byte[] enc4 = StringUtil.toByteArray("6318e9fb5c0d05e5307e1683436e903293ac4642358aaa223d7163013aba87e2dfda8e60c6860e29a1e92686163ea0b9175f329ca3b131a1edd3a77759a8b97bad6a4f8f4396f28cf6f39ca58112e48160d6e203daa5856f3aca5ffed577af499408e3dfd233e3e604dbe34a9c4c9082de65527cac6331d29dc80e0508a0fa7122e7f329f6cca5cfa34d4d1da417805457e008bec549e478ff9e12a763c477d15bbb78f5b69bd57830fc2c4ed686d79bc72a95d85f88134c6b0afe56a8ccfbc855828bb339bd17909cf1d70de3335ae07039093e606d655365de6550b872cd6de1d440ee031b61945f629ad8a353b0d40939e96a3c450d2a8d5eee9f678093c8");
		 byte[] enc5 = StringUtil.toByteArray("75290872ccfd4a4505660d651f56da6daa09ca1301d890632f6a992f3d565cee464afded40ed3b5be9356714ea5aa7655f4a1366c2f17c728f6f2c5a5d1f8e28429bc4e6f8f2cff8da8dc0e0a9808e45fd09ea2fa40cb2b6ce6ffff5c0e159d11b68d90a85f7b84e103b09e682666480c657505c0929259468a314786d74eab131573cf234bf57db7d9e66cc6748192e002dc0deea930585f0831fdcd9bc33d51f79ed2ffc16bcf4d59812fcebcaa3f9069b0e445686d644c25ccf63b456ee5fa6ffe96f19cdf751fed9eaf35957754dbf4bfea5216aa1844dc507cb2d080e722eba150308c2b5ff1193620f1766ecf4481bafb943bd292877f2136ca494aba0");
		 byte[] enc6 = StringUtil.toByteArray("2d207a73432a8fb4c03051b3f73b28a61764098dfa34c47a20995f8115aa6816679b557e82dbee584908c6e69782d7deb34dbd65af063d57fca76a5fd069492fd6068d9984d209350565a62e5c77f23038c12cb10c6634709b547c46f6b4a709bd85ca122d74465ef97762c29763e06dbc7a9e738c78bfca0102dc5e79d65b973f28240caab2e161a78b57d262457ed8195d53e3c7ae9da021883c6db7c24afdd2322eac972ad3c354c5fcef1e146c3a0290fb67adf007066e00428d2cec18ce58f9328698defef4b2eb5ec76918fde1c198cbb38b7afc67626a9aefec4322bfd90d2563481c9a221f78c8272c82d1b62ab914e1c69f6af6ef30ca5260db4a46");
		 
		 
		ArrayList<byte[]> ciphers = add(enc1, enc2, enc3, enc4, enc5, enc6);
		ArrayList<byte[]> data = add(msg1, msg2, msg3, msg4, msg5, msg6);
		 
		 
		 KeyFactory kf = KeyFactory.getInstance("RSA");
		 PrivateKey prik = kf.generatePrivate(pris);
		 PublicKey pubk = kf.generatePublic(pubs);
		
		 
		 ArrayList<byte[]> results = decrypt(new KeyPair(pubk, prik),  ciphers);
		 
	
		 compare(results, data);
	}
	
	
	
	private static void compare(ArrayList<byte[]> aResults,ArrayList<byte[]> aData)
	{
		assertEquals(true, Arrays.equals(aResults.get(0), aData.get(0)));
		assertEquals(true, Arrays.equals(aResults.get(1), aData.get(1)));
		assertEquals(true, Arrays.equals(aResults.get(2), aData.get(2)));
		assertEquals(true, Arrays.equals(aResults.get(3), aData.get(3)));
		assertEquals(true, Arrays.equals(aResults.get(4), aData.get(4)));
		assertEquals(true, Arrays.equals(aResults.get(5), aData.get(5)));
	}

}

