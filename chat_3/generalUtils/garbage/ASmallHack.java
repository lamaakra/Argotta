package com.example.ahmad.chat_3.generalUtils.garbage;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class ASmallHack {

    // todo save this to a jsn file in assets then load it
    public String getLangCode(String lang) {
        String json = "{\n" +
                "    \"Afar\": \"aa\",\n" +
                "    \"Abkhaz\": \"ab\",\n" +
                "    \"Avestan\": \"ae\",\n" +
                "    \"Afrikaans\": \"af\",\n" +
                "    \"Akan\": \"ak\",\n" +
                "    \"Amharic\": \"am\",\n" +
                "    \"Aragonese\": \"an\",\n" +
                "    \"Arabic\": \"ar\",\n" +
                "    \"Assamese\": \"as\",\n" +
                "    \"Avaric\": \"av\",\n" +
                "    \"Aymara\": \"ay\",\n" +
                "    \"Azerbaijani\": \"az\",\n" +
                "    \"South Azerbaijani\": \"az\",\n" +
                "    \"Bashkir\": \"ba\",\n" +
                "    \"Belarusian\": \"be\",\n" +
                "    \"Bulgarian\": \"bg\",\n" +
                "    \"Bihari\": \"bh\",\n" +
                "    \"Bislama\": \"bi\",\n" +
                "    \"Bambara\": \"bm\",\n" +
                "    \"Bengali; Bangla\": \"bn\",\n" +
                "    \"Tibetan Standard, Tibetan, Central\": \"bo\",\n" +
                "    \"Breton\": \"br\",\n" +
                "    \"Bosnian\": \"bs\",\n" +
                "    \"Catalan; Valencian\": \"ca\",\n" +
                "    \"Chechen\": \"ce\",\n" +
                "    \"Chamorro\": \"ch\",\n" +
                "    \"Corsican\": \"co\",\n" +
                "    \"Cree\": \"cr\",\n" +
                "    \"Czech\": \"cs\",\n" +
                "    \"Old Church Slavonic, Church Slavonic, Old Bulgarian\": \"cu\",\n" +
                "    \"Chuvash\": \"cv\",\n" +
                "    \"Welsh\": \"cy\",\n" +
                "    \"Danish\": \"da\",\n" +
                "    \"German\": \"de\",\n" +
                "    \"Divehi; Dhivehi; Maldivian;\": \"dv\",\n" +
                "    \"Dzongkha\": \"dz\",\n" +
                "    \"Ewe\": \"ee\",\n" +
                "    \"Greek, Modern\": \"el\",\n" +
                "    \"English\": \"en\",\n" +
                "    \"Esperanto\": \"eo\",\n" +
                "    \"Spanish; Castilian\": \"es\",\n" +
                "    \"Estonian\": \"et\",\n" +
                "    \"Basque\": \"eu\",\n" +
                "    \"Persian (Farsi)\": \"fa\",\n" +
                "    \"Fula; Fulah; Pulaar; Pular\": \"ff\",\n" +
                "    \"Finnish\": \"fi\",\n" +
                "    \"Fijian\": \"fj\",\n" +
                "    \"Faroese\": \"fo\",\n" +
                "    \"French\": \"fr\",\n" +
                "    \"Western Frisian\": \"fy\",\n" +
                "    \"Irish\": \"ga\",\n" +
                "    \"Scottish Gaelic; Gaelic\": \"gd\",\n" +
                "    \"Galician\": \"gl\",\n" +
                "    \"Guaraní\": \"gn\",\n" +
                "    \"Gujarati\": \"gu\",\n" +
                "    \"Manx\": \"gv\",\n" +
                "    \"Hausa\": \"ha\",\n" +
                "    \"Hebrew (modern)\": \"he\",\n" +
                "    \"Hindi\": \"hi\",\n" +
                "    \"Hiri Motu\": \"ho\",\n" +
                "    \"Croatian\": \"hr\",\n" +
                "    \"Haitian; Haitian Creole\": \"ht\",\n" +
                "    \"Hungarian\": \"hu\",\n" +
                "    \"Armenian\": \"hy\",\n" +
                "    \"Herero\": \"hz\",\n" +
                "    \"Interlingua\": \"ia\",\n" +
                "    \"Indonesian\": \"id\",\n" +
                "    \"Interlingue\": \"ie\",\n" +
                "    \"Igbo\": \"ig\",\n" +
                "    \"Nuosu\": \"ii\",\n" +
                "    \"Inupiaq\": \"ik\",\n" +
                "    \"Ido\": \"io\",\n" +
                "    \"Icelandic\": \"is\",\n" +
                "    \"Italian\": \"it\",\n" +
                "    \"Inuktitut\": \"iu\",\n" +
                "    \"Japanese\": \"ja\",\n" +
                "    \"Javanese\": \"jv\",\n" +
                "    \"Georgian\": \"ka\",\n" +
                "    \"Kongo\": \"kg\",\n" +
                "    \"Kikuyu, Gikuyu\": \"ki\",\n" +
                "    \"Kwanyama, Kuanyama\": \"kj\",\n" +
                "    \"Kazakh\": \"kk\",\n" +
                "    \"Kalaallisut, Greenlandic\": \"kl\",\n" +
                "    \"Khmer\": \"km\",\n" +
                "    \"Kannada\": \"kn\",\n" +
                "    \"Korean\": \"ko\",\n" +
                "    \"Kanuri\": \"kr\",\n" +
                "    \"Kashmiri\": \"ks\",\n" +
                "    \"Kurdish\": \"ku\",\n" +
                "    \"Komi\": \"kv\",\n" +
                "    \"Cornish\": \"kw\",\n" +
                "    \"Kyrgyz\": \"ky\",\n" +
                "    \"Latin\": \"la\",\n" +
                "    \"Luxembourgish, Letzeburgesch\": \"lb\",\n" +
                "    \"Ganda\": \"lg\",\n" +
                "    \"Limburgish, Limburgan, Limburger\": \"li\",\n" +
                "    \"Lingala\": \"ln\",\n" +
                "    \"Lao\": \"lo\",\n" +
                "    \"Lithuanian\": \"lt\",\n" +
                "    \"Luba-Katanga\": \"lu\",\n" +
                "    \"Latvian\": \"lv\",\n" +
                "    \"Malagasy\": \"mg\",\n" +
                "    \"Marshallese\": \"mh\",\n" +
                "    \"Māori\": \"mi\",\n" +
                "    \"Macedonian\": \"mk\",\n" +
                "    \"Malayalam\": \"ml\",\n" +
                "    \"Mongolian\": \"mn\",\n" +
                "    \"Marathi (Marāṭhī)\": \"mr\",\n" +
                "    \"Malay\": \"ms\",\n" +
                "    \"Maltese\": \"mt\",\n" +
                "    \"Burmese\": \"my\",\n" +
                "    \"Nauru\": \"na\",\n" +
                "    \"Norwegian Bokmål\": \"nb\",\n" +
                "    \"North Ndebele\": \"nd\",\n" +
                "    \"Nepali\": \"ne\",\n" +
                "    \"Ndonga\": \"ng\",\n" +
                "    \"Dutch\": \"nl\",\n" +
                "    \"Norwegian Nynorsk\": \"nn\",\n" +
                "    \"Norwegian\": \"no\",\n" +
                "    \"South Ndebele\": \"nr\",\n" +
                "    \"Navajo, Navaho\": \"nv\",\n" +
                "    \"Chichewa; Chewa; Nyanja\": \"ny\",\n" +
                "    \"Occitan\": \"oc\",\n" +
                "    \"Ojibwe, Ojibwa\": \"oj\",\n" +
                "    \"Oromo\": \"om\",\n" +
                "    \"Oriya\": \"or\",\n" +
                "    \"Ossetian, Ossetic\": \"os\",\n" +
                "    \"Panjabi, Punjabi\": \"pa\",\n" +
                "    \"Pāli\": \"pi\",\n" +
                "    \"Polish\": \"pl\",\n" +
                "    \"Pashto, Pushto\": \"ps\",\n" +
                "    \"Portuguese\": \"pt\",\n" +
                "    \"Quechua\": \"qu\",\n" +
                "    \"Romansh\": \"rm\",\n" +
                "    \"Kirundi\": \"rn\",\n" +
                "    \"Romanian\": \"ro\",\n" +
                "    \"Russian\": \"ru\",\n" +
                "    \"Kinyarwanda\": \"rw\",\n" +
                "    \"Sanskrit (Saṁskṛta)\": \"sa\",\n" +
                "    \"Sardinian\": \"sc\",\n" +
                "    \"Sindhi\": \"sd\",\n" +
                "    \"Northern Sami\": \"se\",\n" +
                "    \"Sango\": \"sg\",\n" +
                "    \"Sinhala, Sinhalese\": \"si\",\n" +
                "    \"Slovak\": \"sk\",\n" +
                "    \"Slovene\": \"sl\",\n" +
                "    \"Samoan\": \"sm\",\n" +
                "    \"Shona\": \"sn\",\n" +
                "    \"Somali\": \"so\",\n" +
                "    \"Albanian\": \"sq\",\n" +
                "    \"Serbian\": \"sr\",\n" +
                "    \"Swati\": \"ss\",\n" +
                "    \"Southern Sotho\": \"st\",\n" +
                "    \"Sundanese\": \"su\",\n" +
                "    \"Swedish\": \"sv\",\n" +
                "    \"Swahili\": \"sw\",\n" +
                "    \"Tamil\": \"ta\",\n" +
                "    \"Telugu\": \"te\",\n" +
                "    \"Tajik\": \"tg\",\n" +
                "    \"Thai\": \"th\",\n" +
                "    \"Tigrinya\": \"ti\",\n" +
                "    \"Turkmen\": \"tk\",\n" +
                "    \"Tagalog\": \"tl\",\n" +
                "    \"Tswana\": \"tn\",\n" +
                "    \"Tonga (Tonga Islands)\": \"to\",\n" +
                "    \"Turkish\": \"tr\",\n" +
                "    \"Tsonga\": \"ts\",\n" +
                "    \"Tatar\": \"tt\",\n" +
                "    \"Twi\": \"tw\",\n" +
                "    \"Tahitian\": \"ty\",\n" +
                "    \"Uyghur, Uighur\": \"ug\",\n" +
                "    \"Ukrainian\": \"uk\",\n" +
                "    \"Urdu\": \"ur\",\n" +
                "    \"Uzbek\": \"uz\",\n" +
                "    \"Venda\": \"ve\",\n" +
                "    \"Vietnamese\": \"vi\",\n" +
                "    \"Volapük\": \"vo\",\n" +
                "    \"Walloon\": \"wa\",\n" +
                "    \"Wolof\": \"wo\",\n" +
                "    \"Xhosa\": \"xh\",\n" +
                "    \"Yiddish\": \"yi\",\n" +
                "    \"Yoruba\": \"yo\",\n" +
                "    \"Zhuang, Chuang\": \"za\",\n" +
                "    \"Chinese\": \"zh\",\n" +
                "    \"Zulu\": \"zu\"\n" +
                "}";

        Map<String, String> map = new Gson().fromJson(json, new HashMap<String, String>().getClass());
        return map.get(lang);
    }
}
