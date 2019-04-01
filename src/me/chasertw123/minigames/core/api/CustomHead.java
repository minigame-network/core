package me.chasertw123.minigames.core.api;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.chasertw123.minigames.core.utils.items.cItemStack;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Field;
import java.util.UUID;

/**
 * Created by Chase on 7/23/2017.
 */
public enum CustomHead {

    // General Skull Textures
    QUESTION_MARK("http://textures.minecraft.net/texture/5359d91277242fc01c309accb87b533f1929be176ecba2cde63bf635e05e699b"),
    RAINBOW_QUESTION_MARK("http://textures.minecraft.net/texture/9d9cc58ad25a1ab16d36bb5d6d493c8f5898c2bf302b64e325921c41c35867"),
    MAIL_PACKAGE("http://textures.minecraft.net/texture/b2034fc7985a110fe7acc873569263fb4b6b3826298dd8be3dfbdbeeddad"),
    ARROW_LEFT("http://textures.minecraft.net/texture/bd69e06e5dadfd84e5f3d1c21063f2553b2fa945ee1d4d7152fdc5425bc12a9"),
    ARROW_RIGHT("http://textures.minecraft.net/texture/19bf3292e126a105b54eba713aa1b152d541a1d8938829c56364d178ed22bf"),

    // Alphabet Skull Textures
    LETTERCUBE_A("http://textures.minecraft.net/texture/9c60da2944a177dd08268fbec04e40812d1d929650be66529b1ee5e1e7eca"),
    LETTERCUBE_B("http://textures.minecraft.net/texture/8041f5e86983d36eaec4e167b2bbb5a3727607cde88f7555ca1b522a039bb"),
    LETTERCUBE_C("http://textures.minecraft.net/texture/d945996c8ae91e376196d4dc676fec31feac790a2f195b2981a703ca1d16cb6"),
    LETTERCUBE_D("http://textures.minecraft.net/texture/1641150f481e8492f7128c948996254d2d91fc90f5a8ff4d8ac5c39a6a88a"),
    LETTERCUBE_E("http://textures.minecraft.net/texture/db251487ff8eef2ebc7a57dab6e3d9f1db7fc926ddc66fea14afe3dff15a45"),
    LETTERCUBE_F("http://textures.minecraft.net/texture/7e433656b443668ed03dac8c442722a2a41221be8bb48e23b35bd8c2e59f63"),
    LETTERCUBE_G("http://textures.minecraft.net/texture/995863b73637605feacbb173b77d5e155e65204c78d5c7911f738f28deb60"),
    LETTERCUBE_H("http://textures.minecraft.net/texture/3c1d358d927074289cc26bff5b1240746f9f4f0cc46f942f5981c6595f72dd"),
    LETTERCUBE_I("http://textures.minecraft.net/texture/8f2295865bda4e47979d36b8a887a75a13b034e6988f78670b64a1e6442c"),
    LETTERCUBE_J("http://textures.minecraft.net/texture/e34462b55d7f5823680ad13f2adbd7d1ed46ba5101017ed4b37aeeeb775d"),
    LETTERCUBE_K("http://textures.minecraft.net/texture/773325a935c067b6ef227367f62ca4bf49f67adb9f6da32091e2d32c5dde328"),
    LETTERCUBE_L("http://textures.minecraft.net/texture/25a1e3328c571aa495d9c5f494815cca176c3acb184feb5a7b9c96ce8e52fce"),
    LETTERCUBE_M("http://textures.minecraft.net/texture/d467bf6be95e5c8e9d01977a2f0c487ed5b0de5c87963a2eb15411c442fb2b"),
    LETTERCUBE_N("http://textures.minecraft.net/texture/823e434d6395fe7e63492431bdee5782bd5ee5bc8cab7559467bdd1f93b925a"),
    LETTERCUBE_O("http://textures.minecraft.net/texture/88445466bdc5ad5bcea82239c4e1b510f6ea5262d82d8a96d7291c342fb89"),
    LETTERCUBE_P("http://textures.minecraft.net/texture/f9de601dee3ffeca4d54595f844201d0ed2091acec4548c696bb16a8a158f6"),
    LETTERCUBE_Q("http://textures.minecraft.net/texture/66ca769bde25d4cc41e19e42adc35ab4c1557b76af232649acc9967ff198f13"),
    LETTERCUBE_R("http://textures.minecraft.net/texture/67a188805162ca5dd4f4649c661d3f6d23c42662aef01645b1a97f78b3f13219"),
    LETTERCUBE_S("http://textures.minecraft.net/texture/60d09dfd9f5de6243233e0e3325b6c3479335e7ccf13f2448d4e1f7fc4a0df"),
    LETTERCUBE_T("http://textures.minecraft.net/texture/64c75619b91d241f678350ad9237c134c5e08d87d6860741ede306a4ef91"),
    LETTERCUBE_U("http://textures.minecraft.net/texture/e9f6d2c6d5285f882ae55d1e91b8f9efdfc9b377208bf4c83f88dd156415e"),
    LETTERCUBE_V("http://textures.minecraft.net/texture/dce27a153635f835237d85c6bf74f5b1f2e638c48fee8c83038d0558d41da7"),
    LETTERCUBE_W("http://textures.minecraft.net/texture/aedcf4ffcb53b56d42baac9d0dfb118e343462327442dd9b29d49f50a7d38b"),
    LETTERCUBE_X("http://textures.minecraft.net/texture/83618ff1217640bec5b525fa2a8e671c75d2a7d7cb2ddc31d79d9d895eab1"),
    LETTERCUBE_Y("http://textures.minecraft.net/texture/d9c1d29a38bcf113b7e8c34e148a79f9fe41edf41aa8b1de873bb1d433b3861"),
    LETTERCUBE_Z("http://textures.minecraft.net/texture/b9295734195d2c7fa389b98757e9686ce6437c16c58bdf2b4cd538389b5912"),

    // Treasure Chest Skull Textures
    TREASURE_CHEST_RED("http://textures.minecraft.net/texture/d8c1e1c62dc695eb90fa192da6aca49ab4f9dffb6adb5d2629ebfc9b2788fa2"),
    TREASURE_CHEST_GREEN("http://textures.minecraft.net/texture/78f88b161763f62e4c51f5eb1d38faf3b82c48a839ac3171229557ade427434"),
    TREASURE_CHEST_BLUE("http://textures.minecraft.net/texture/8cc79954d350a98c7217256831f65c62a428074b6e4ae9eede7a44f9de4a7"),
    TREASURE_CHEST_BLACK("http://textures.minecraft.net/texture/7cd3d99ad2ee63fbe9f13db3ac647b2285b217ab2ddc31f64cab45fbbf7a4"),
    TREASURE_CHEST_GRAY("http://textures.minecraft.net/texture/e1daee3f453a38e35d8cab8353c5d28cce1b675ff94b92e5c4af2d6452031a0"),
    TREASURE_CHEST_PURPLE("http://textures.minecraft.net/texture/6436be92887dc40cdf372bb3c3811baf5c36ff131161b82f067564136bbab"),
    TREASURE_CHEST_BROWN("http://textures.minecraft.net/texture/776ad9ff7d606f31adb624b1496f67eb6d269944e147052e57e48741b1482a4"),

    // Color Skull Textures
    ALICE_BLUE("http://textures.minecraft.net/texture/b58caab4c9416395b58e8cf6f0cb8648f9fadb1d80d12166dce4a3d2725fa53"),
    ANTIQUE_WHITE("http://textures.minecraft.net/texture/fc90e2b8a117ad9280b2a9fcbb0a9a2d9184f38dc3381cf71279f030a76f74"),
    AQUA_MARINE("http://textures.minecraft.net/texture/57062297f26e509ce6a24c99201a8f8b4ae030d58f41e73cd93d7bd77e647"),
    AZURE("http://textures.minecraft.net/texture/258ab057922089fa42f04af7b7fb759714baa2d991d948679f0a144a56d338"),
    BEIGE("http://textures.minecraft.net/texture/f4a176fc0b0936bc83f79dec14ba441a323e899eff4579696b33ed8eea82e"),
    BISQUE("http://textures.minecraft.net/texture/fef4ad4eaa29c29b694c24ccadc0a572261156198bf93ffeef2fa9ac712186"),
    BLACK("http://textures.minecraft.net/texture/967a2f218a6e6e38f2b545f6c17733f4ef9bbb288e75402949c052189ee"),
    BLANCHED_ALMOND("http://textures.minecraft.net/texture/31b3422c258444cb271073453195145daf80636f5d4e95dd5383f2da92112885"),
    BLUE("http://textures.minecraft.net/texture/b8afa1555e9f876481e3c4299ec6e91d22b4075e67e58ef80dcd190ace6519f"),
    BLUE_VIOLET("http://textures.minecraft.net/texture/8e63a6fc5e8e5340e3986d496a875e4d7d94e683124a529ccfff77f9bab433"),
    BROWN("http://textures.minecraft.net/texture/2da1508d47ed73b5c515e3b93928b728e4bc6278569a79b3723ab6972ce05357"),
    BURLY_WOOD("http://textures.minecraft.net/texture/3e2d303866adefdd581957924723a0a3c5ddb37ab6f3f079d58ef2acaf66b2d"),
    CADET_BLUE("http://textures.minecraft.net/texture/db67772b93d4aba189ce2c78aa6e54181aea21f4b72ebcd97f62a3cf87e0a"),
    CHART_REUSE("http://textures.minecraft.net/texture/73ccb4db2c196928343e6c1c79a27d737e655d0c9f9169b7a88d1744571417"),
    CHOCOLATE("http://textures.minecraft.net/texture/91853e396e5f8a6ca428a9fe4f6dc4fbdc51838235c3eb81f948bf4336bfd5"),
    CORAL("http://textures.minecraft.net/texture/ad59ead019c2e5c64cb8397d3a79db9fd0869c47c58177d5879c3c7419ea37a"),
    CORN_FLOWER_BLUE("http://textures.minecraft.net/texture/a653cdda519f879eb212a6418d2735ba6cd5ded382f329a6b54a9551a57968cd"),
    CORN_SILK("http://textures.minecraft.net/texture/59970d085bf5aae875165f8337896242c12cd11299df89891a8a813adeb8"),
    CRIMSON("http://textures.minecraft.net/texture/d2932b66decaeff6ebdc7c5be6b2467aa6f14b746388a06a2e1e1a8463e9e122"),
    CYAN("http://textures.minecraft.net/texture/07c78f3ee783feecd2692eba54851da5c4323055ebd2f683cd3e8302fea7c"),
    DARK_BLUE("http://textures.minecraft.net/texture/6a46053012c68f289abcfb17ab8042d5afba95dcaa99c99c1e0360886d35"),
    DARK_CYAN("http://textures.minecraft.net/texture/95b9a48467f0212aa68864e6342116f8f79a275454bf215f67f701a6f2c818"),
    DARK_GOLDEN_ROD("http://textures.minecraft.net/texture/d37eef4485be0a723a6a90939aca15fdef239b8a39265c7c883598d36ae5"),
    DARK_GRAY("http://textures.minecraft.net/texture/f9ba7fef6a1a8bd899abae4a5b54cb0ece53badc677c1668bee0a4621a8"),
    DARK_GREEN("http://textures.minecraft.net/texture/2c9e601ed9198dbb34c51ddf323929f01a5f958ab11133e3e0407b698393b3f"),
    DARK_KHAKI("http://textures.minecraft.net/texture/1ebe98d2a639c5c1f517c7b7d208e45239d1e6f6399a282fc66e915a361e417"),
    DARK_MAGENTA("http://textures.minecraft.net/texture/a5fc2e5d75106d491154450152bf4223e9dc92916c52118f64a812436c736"),
    DARK_OLIVE_GREEN("http://textures.minecraft.net/texture/2a6fe0e5a5925e5241804aafac6c4f21a370cc2ffc4d87657de57124d6b2820"),
    DARK_ORANGE("http://textures.minecraft.net/texture/e79add3e5936a382a8f7fdc37fd6fa96653d5104ebcadb0d4f7e9d4a6efc454"),
    DARK_ORCHID("http://textures.minecraft.net/texture/73aafdfcf052122fa5d2a293dfbe35b8dea7db89d2303db32a6577171a9d87a1"),
    DARK_RED("http://textures.minecraft.net/texture/df4dc3c3753bf5b0b7f081cdb49b83d37428a12e4187f6346dec06fac54ce"),
    DARK_SALMON("http://textures.minecraft.net/texture/c13bd141f215f3d83262f377587399722e1c8ce68860f43f8b299d2cb43315"),
    DARK_SEA_GREEN("http://textures.minecraft.net/texture/e6341e59a38582330bfa2163628f53d94d8212993641cc93a2bea304d3af9"),
    DARK_SLATE_BLUE("http://textures.minecraft.net/texture/9f1e69ec52ab1e3bd185591c1642b5b74498e11a53140eff55f5321988fc996"),
    DARK_SLATE_GRAY("http://textures.minecraft.net/texture/6ce143728ef1277820f83ffcd44a57e4548b8f2b54f2d4854bb45c474123d66"),
    DARK_TURQUOISE("http://textures.minecraft.net/texture/4c374acea78efbefa798be1b27e9714c36411e202eecd37b8cfcfd249a862e"),
    DARK_VIOLET("http://textures.minecraft.net/texture/593f67f9f730d42fda8de69565ea55892c5f85d9cae6dd6fcba5d26f1e7238d1"),
    DEEP_PINK("http://textures.minecraft.net/texture/1e3dcdbeea35f7ecb16674ac6ffed7906775139e22c78bf7295386d3194e9f6"),
    DEEP_SKY_BLUE("http://textures.minecraft.net/texture/83d1c463756ca33c9d6cebd2423795d8a4d87e7194b503579e8e11225166fe"),
    DIM_GRAY("http://textures.minecraft.net/texture/608f323462fb434e928bd6728638c944ee3d812e162b9c6ba070fcac9bf9"),
    DODGER_BLUE("http://textures.minecraft.net/texture/bef7b6829fc48758cb25ab93f28bf794d92ace0161f809a2aadd3bb12b23014"),
    FIREBRICK("http://textures.minecraft.net/texture/c47237437eef639441b92b217efdc8a72514a9567c6b6b81b553f4ef4ad1cae"),
    FLORAL_WHITE("http://textures.minecraft.net/texture/b3cd3c1257adc010d693260b351cd9781b98efb9826c36e41b9d8cb5706a89"),
    FOREST_GREEN("http://textures.minecraft.net/texture/78d58a7651fedae4c03efebc226c03fd791eb74a132babb974e8d838ac6882"),
    GAINSBORO("http://textures.minecraft.net/texture/fd9f5264389a38d4d2c76a9b411687c451681c9b8ad20de5a4afe36975651"),
    GHOST_WHITE("http://textures.minecraft.net/texture/ad93117b9e180e0dc39e5e8a0508482cf1f60e446e022978fe0651a562a597f"),
    GOLD("http://textures.minecraft.net/texture/143c79cd9c2d3187ea03245fe2128e0d2abbe7945214bc5834dfa403c134e27"),
    GOLDEN_ROD("http://textures.minecraft.net/texture/97c2d5eee84bba1d7e94f933a0a556ed7ea4e4fa65e8e9f56325813b"),
    GRAY("http://textures.minecraft.net/texture/2a17e97037ce353f85f5c65df435d29449a88da4442e4361cf99abbe1f892fb"),
    GREEN("http://textures.minecraft.net/texture/8e9b27fccd80921bd263c91dc511d09e9a746555e6c9cad52e8562ed0182a2f"),
    GREEN_YELLOW("http://textures.minecraft.net/texture/8d82fcfa5578715c0d248e0aac42ab572e9a826ed3dad9dc66c9926e8473ed"),
    HONEYDEW("http://textures.minecraft.net/texture/337ae8d770b55dba6bbca9d0a086f5c384ef951e8f48e84fb3b71e24ec11db"),
    HOT_PINK("http://textures.minecraft.net/texture/73b0af83d0c728aeeca470f08a1d75f41cee253a3573ba4157ca2433e6c36"),
    INDIAN_RED("http://textures.minecraft.net/texture/c2569cc67d6794beb03e2cb242bd107e49ee94c83d64c23b77c798a2cf1231c"),
    INDIGO("http://textures.minecraft.net/texture/565e24eb655d507763254b4364e9fe3c36b7dba366c6347376f97bc97e5c0"),
    IVORY("http://textures.minecraft.net/texture/8021e73d5f57539d3a9f5bc8426cb28a737bef186c155ec6563fb3c111c2b4b3"),
    KHAKI("http://textures.minecraft.net/texture/914c944218835585d8e9a55862b1f44578e3c3b54e57aa25b4d29746439c7cad"),
    LAVENDER("http://textures.minecraft.net/texture/c6c134a8a337a535f13bb69df61d4be3285b857b7cbc6b0b1feb7320b57db2"),
    LAWN_GREEN("http://textures.minecraft.net/texture/e9e4bdcf172d5dc77c2bd4e37ad985399a9f2cdebf72463929ea4b666ef6f80"),
    LEMON_CHIFFON("http://textures.minecraft.net/texture/e588123a420dfd671e5c32a17c87f7a51b3934c33b1bb81e3fc0fef7fb40"),
    LIGHT_BLUE("http://textures.minecraft.net/texture/33f75cc2b7f3b2418242e454187156c51b058e43425489a80a5568542b83c94"),
    LIGHT_CORAL("http://textures.minecraft.net/texture/fe54def1477051e2333e32d5b16c22b9d640d3ab99abb844b4e5dfc52da70b3"),
    LIGHT_CYAN("http://textures.minecraft.net/texture/bebd8fae89baa7fe477ab3bf2ee3f7e71ca81ebbe5503c92902898f12b246"),
    LIGHT_GOLDEN_ROD_YELLOW("http://textures.minecraft.net/texture/308eaccefd5b6676d05427af1fa9852403f872e5ccaf23fce35e4d530d4e37"),
    LIGHT_GRAY("http://textures.minecraft.net/texture/38e2957699bc98a4b5d634ab71867eeb186b934bdb65d2c4b9dcc2b613cf5"),
    LIGHT_GREEN("http://textures.minecraft.net/texture/23185046a35ac84059efeb52241be03c8a9499d28da7edca2fce375c85185879"),
    LIGHT_PINK("http://textures.minecraft.net/texture/ca5b93ac4ade53a4ff8cdb82eaaef006fe7291f649c3243ed6fc38fc9b5c68"),
    LIGHT_SALMON("http://textures.minecraft.net/texture/7bfeffa72168d435d8493b3320f5ee34ec289f9193b89c42c552d71f38dd39c"),
    LIGHT_SEA_GREEN("http://textures.minecraft.net/texture/2f18543e7e8a5cd8fe6ec4656c478b5f90574436a5d1f292a5959b80bccc91"),
    LIGHT_SKY_BLUE("http://textures.minecraft.net/texture/3ef2485772e5de65b689664ee097cb6e4bea030fb7787c8a57827d0a67f4c"),
    LIGHT_SLATE_GRAY("http://textures.minecraft.net/texture/82b22bdae799083ad87d9d251dec8bd5d5744fbe3b8a5ba4091ba55974b85b"),
    LIGHT_STEEL_BLUE("http://textures.minecraft.net/texture/58ffb8d9a81b8ded7a8ac3e5a2d95bd143083aaf2057a64d7a9f6077d87ef5"),
    LIGHT_YELLOW("http://textures.minecraft.net/texture/1d36a361c21d8f7bd4ac638575c3b677f0be1af818f4c1a2847051553143d91e"),
    LIME("http://textures.minecraft.net/texture/d27ca46f6a9bb89a24fcaf4cc0acf5e8285a66db7521378ed2909ae449697f"),
    LIME_GREEN("http://textures.minecraft.net/texture/22d145c93e5eac48a661c6f27fdaff5922cf433dd627bf23eec378b9956197"),
    LINEN("http://textures.minecraft.net/texture/5c04d7683c0b01013b1d2eb6c9b9145812bbf6f65f3f92a5b37c747d55d4c9"),
    MAGENTA("http://textures.minecraft.net/texture/3ef0c5773df560cc3fc73b54b5f08cd69856415ab569a37d6d44f2f423df20"),
    MAROON("http://textures.minecraft.net/texture/b439a71957cc1dd4546f4132965effbe3429c889b1c6ba7212cb4b8c03540f8"),
    MEDIUM_AQUA_MARINE("http://textures.minecraft.net/texture/5218c496dfbdeb4553a6d32b274b9956b7e16a9e4dafc9b33fcf1897b8"),
    MEDIUM_BLUE("http://textures.minecraft.net/texture/a2cd272eeb38bf783a98a46fa1e2e8d462d852fbaaedef0dce2c1f717a2a"),
    MEDIUM_ORCHID("http://textures.minecraft.net/texture/e4576e1d4311450bc5b7e4159df56bf9be2796a38ce05143828b7c141cbed"),
    MEDIUM_PURPLE("http://textures.minecraft.net/texture/f617d17132d95b0b3f9f207ba294ce93bd7a2103d2ff06296616b4ad178cc"),
    MEDIUM_SEA_GREEN("http://textures.minecraft.net/texture/2cdbed979523be1c56ec31cb4f5c483baf8a6590ddf8dbb4d72dbd592385a2f9"),
    MEDIUM_SLATE_BLUE("http://textures.minecraft.net/texture/28d86b17c0df6a83eb8cfa769b3b60b3c0835f12e1f9fff9494edfd5f28f55bb"),
    MEDIUM_SPRING_GREEN("http://textures.minecraft.net/texture/69eeb3519ddc14d0a7fce41b5fabf94219296a231ddced202df05aec156221"),
    MEDIUM_TURQUOISE("http://textures.minecraft.net/texture/d216caaaaa3f616793d18e943cd3a46331343bb6e7742e5cd781fffedd604b"),
    MEDIUM_VIOLET_RED("http://textures.minecraft.net/texture/57e31af323675e0f6ad3737b1eab06e8daf14d3cb97d41580c21237b5d7fe83"),
    MIDNIGHT_BLUE("http://textures.minecraft.net/texture/656761b572cfe22ba345dc7ad066a079bd45a7365567c226114a67fd7dba83"),
    MINT_CREAM("http://textures.minecraft.net/texture/26a6bfd51d8ee57ce9c3bc86fd4cc44632d1551599e9cac2f2a5e31fcbdd"),
    MISTY_ROSE("http://textures.minecraft.net/texture/d4106e89ad6f2476496d8dadb7f3f30b41e8d638e3212680d567dfeeb7f6d6"),
    MOCCASIN("http://textures.minecraft.net/texture/daf75eb0175dab83b21fb8786fa6d952d56499762836a1221c9d5cb72b5482"),
    NAVAJO_WHITE("http://textures.minecraft.net/texture/dde5bcedc1e02c6b6767a3bdc6a7ad3f2199166a7cfcc97f9a035622e9638"),
    NAVY("http://textures.minecraft.net/texture/7dd3ede0ad53768abdce493fbf3c2359dc87ec55d2fceeb17754ed590e41a"),
    OLD_LACE("http://textures.minecraft.net/texture/a5cfce3a821452f1df4ad4f25ff4192acff5584c4eed17013127b859261f2"),
    OLIVE("http://textures.minecraft.net/texture/1d8b80a472f56ab8ed20364a33d407f4e41cf09150e3fd5aa50eb371dc"),
    OLIVE_DRAB("http://textures.minecraft.net/texture/665558b237f174a2887dd432db7f76c877f81593e6ea32e72844d430298cbf31"),
    ORANGE("http://textures.minecraft.net/texture/2c4886ef362b2c823a6aa65241c5c7de71c94d8ec5822c51e96976641f53ea35"),
    ORANGE_RED("http://textures.minecraft.net/texture/bc2e972afa9115b6d32075b1f1b7fed7aa29a5341c1024288361abe8e69b46"),
    ORCHID("http://textures.minecraft.net/texture/6953a56365692bec1c679532074353ce49d6e127aac5b42c7195b7678e3f2"),
    PALE_GOLDEN_ROD("http://textures.minecraft.net/texture/f2c0a6baf8de35ebd61cd6d5686faa89acc832dd2ea66b3c1278b2c343b1c3da"),
    PALE_GREEN("http://textures.minecraft.net/texture/bfaf7aab1e177ad38e51bfc19ab662149c31953a569a40caa81f7a4932069"),
    PALE_TURQUOISE("http://textures.minecraft.net/texture/7e8fbb1695914a65f2f26d62a75c5e599c699796cc75c6aa2902aaefdfb416e"),
    PALE_VIOLET_RED("http://textures.minecraft.net/texture/e4894855e36432fbfb14df55c364792b824ab0fa0f73938f168b97747b9e53b"),
    PAPAYA_WHIP("http://textures.minecraft.net/texture/71e349b3ebfd57843e259196dd54db20c934da6f69242699ed86de1d245bc"),
    PEACH_PUFF("http://textures.minecraft.net/texture/3bcf24d1cefb81d2f863b17db2fefb0b923ffa6f9e9475e8f675a3370657e"),
    PERU("http://textures.minecraft.net/texture/86bffea628f36ba7857cb32557579b39960de43ed5589aef937a5c6fa2612a"),
    PINK("http://textures.minecraft.net/texture/2a11a51f6576285f9f9c8aa7ded1ee12c202f15d791c732b586ddae7064b0"),
    PLUM("http://textures.minecraft.net/texture/b80afcac28cc710cc45f6e7ffdfa0fd56b43ed4408fb0fe348b0fb26618fed"),
    POWDER_BLUE("http://textures.minecraft.net/texture/e25f69bbd2e7e55cf2b4a925c8a3fe8ff31ebe9c28dee6ddec53199bc1fbbd"),
    PURPLE("http://textures.minecraft.net/texture/a32ae2cb8d2ae615141d2c65892f364fcaddf73fdec99be1aa6874863eeb5c"),
    RED("http://textures.minecraft.net/texture/5fde3bfce2d8cb724de8556e5ec21b7f15f584684ab785214add164be7624b"),
    ROSY_BROWN("http://textures.minecraft.net/texture/b09a7ecfc7ab922368dad1d842de3ff539369a5da6bf895ab3d25cfa040590"),
    ROYAL_BLUE("http://textures.minecraft.net/texture/f8157b4dc5efc217352894471c116d39a034fc397c24539a9d0eeb2a465ca"),
    SADDLE_BROWN("http://textures.minecraft.net/texture/e54e54e47b962b83fea779176e956612c12390b7d0eaa51a621c76255cddf3ab"),
    SALMON("http://textures.minecraft.net/texture/afc556ebd483bfb253db6744f1ee9bda5d5481f316164ca82bcb189edfec4f1f"),
    SANDY_BROWN("http://textures.minecraft.net/texture/ffe799474c289e7fe15adfd949235eb2cd4d42bdf57c3edc328d742ac7579"),
    SEA_GREEN("http://textures.minecraft.net/texture/f1e931ddc19793191e3bb381c84e6ed6955cd93536ba7be108d7864e39adbde"),
    SEA_SHELL("http://textures.minecraft.net/texture/285e657455ac7f9b6a5484fde8fb21ede1349ececabe73dfc2eb3eacf2b485a"),
    SIENNA("http://textures.minecraft.net/texture/f1f5fffc6ef28724fb9a0c110d9fa7d230e3af5e92297e3d62df45271ae1f4"),
    SILVER("http://textures.minecraft.net/texture/1fcc8d26c645c3bad29b2f2a131a628edc9bfcbbe63fd391ce15c2bc89cc90cb"),
    SKY_BLUE("http://textures.minecraft.net/texture/2fb3db103285e92588984b871d8f5b19c89f4e295c99260808a60e43f8bd3d"),
    SLATE_BLUE("http://textures.minecraft.net/texture/62d5c732ae5823efebebc12ebad6b559cbb71d6bc7ece53bc4a983adaae43"),
    SLATE_GRAY("http://textures.minecraft.net/texture/f5b09584559c2f7646a59ce03b6394a0aee0a4386fe7f5d2a9211eaf6346b"),
    SNOW("http://textures.minecraft.net/texture/19b7319ee969b942d3c4e1dcb1f22da261f6999d1eeab045c2eeaa48e73389"),
    SPRING_GREEN("http://textures.minecraft.net/texture/adf782cae2dd5397c4845fe14fba3617246d3c4fa55010d21bb6214be137bd"),
    STEEL_BLUE("http://textures.minecraft.net/texture/cbdd969412df6e0acffbb7a73bfa34110eecc1f51d80ba0da25da439316bc"),
    TAN("http://textures.minecraft.net/texture/c532576b8bc9a876419a39ae4498c456fe9ee5bdc7ba91fd3b2f1b0a7eb91"),
    TEAL("http://textures.minecraft.net/texture/1fdef6929ebaf394bce2ee7ea2adbbb5c83d7ce17e3b8615a928aefabf85b"),
    THISTLE("http://textures.minecraft.net/texture/98b81ec5472e7e46cf4983e37b9430456eeb8825868eff532965fb39824d1a"),
    TOMATO("http://textures.minecraft.net/texture/e36d71843c67f54e13ba6187da48d8f2498c2af8a22f85686417862f61ae161"),
    TURQUOISE("http://textures.minecraft.net/texture/a4413fe767f282780cdec4903b5abd9e91ca596f3286c3df9d46e5647dc"),
    VIOLET("http://textures.minecraft.net/texture/c143f422db8e958bbb5de2368692bde6960e4822678b010e24797e7979b5b7"),
    WHEAT("http://textures.minecraft.net/texture/69cecbe4a7e78ee332d7978b5458f79bcb19ce6385e595176235b2421df6f70"),
    WHITE("http://textures.minecraft.net/texture/366a5c98928fa5d4b5d5b8efb490155b4dda3956bcaa9371177814532cfc"),
    WHITE_SMOKE("http://textures.minecraft.net/texture/ce3acc78b624c070849184e0a266d9e99aa671fb3e38d66c2c3c5191194793"),
    YELLOW("http://textures.minecraft.net/texture/c641682f43606c5c9ad26bc7ea8a30ee47547c9dfd3c6cda49e1c1a2816cf0ba"),
    YELLOW_GREEN("http://textures.minecraft.net/texture/b8fff22c6e6546d0d8eb7f9763398407dd2ab80f74fe3d16b10a983ecaf347e"),

    VOID_ORB("http://textures.minecraft.net/texture/6201ae1a8a04df52656f5e4813e1fbcf97877dbbfbc4268d04316d6f9f753"),

    // PETS
    BLACK_CAT("http://textures.minecraft.net/texture/2f1806417c16ccdc339d44d328251a8d1bbeab945f87a85b48e4b114fdefb"),
    RED_CAT("http://textures.minecraft.net/texture/4b432b7c8468cf6892868d863d38dd9e6b4cc0555768b8e07137f878f33e5b"),
    SIAMESE_CAT("http://textures.minecraft.net/texture/ca7e1cc6ac6fbe29b2aa6c6fbbec5bd785b12df3dc659041c0464b8327ad933");

    private String url;

    CustomHead(String url) {
        this.url = url;
    }

    public cItemStack craftSkull() {

        cItemStack head = new cItemStack(Material.SKULL_ITEM, 1, (short) 3);

        if (url == null || url.isEmpty())
            return head;

        ItemMeta headMeta = head.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField = null;

        try {
            profileField = headMeta.getClass().getDeclaredField("profile");
        } catch (NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }

        profileField.setAccessible(true);

        try {
            profileField.set(headMeta, profile);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }

        head.setItemMeta(headMeta);
        return head;
    }
}
