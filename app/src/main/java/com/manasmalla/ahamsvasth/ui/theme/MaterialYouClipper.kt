package com.manasmalla.ahamsvasth.ui.theme

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class MaterialYouClipper : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(path = drawMaterialYouClipper(size))
    }
}

fun drawMaterialYouClipper(size: Size): Path {
    val path = Path()
    path.moveTo(size.width * 0.4492395, size.height * 0.03555133)
    path.cubicTo(
        size.width * 0.4797529,
        size.height * 0.01416350,
        size.width * 0.5202471,
        size.height * 0.01416350,
        size.width * 0.5507605,
        size.height * 0.03555133
    )
    path.lineTo(size.width * 0.5617871, size.height * 0.04315589)
    path.cubicTo(
        size.width * 0.5666350,
        size.height * 0.04657795,
        size.width * 0.5717681,
        size.height * 0.04952471,
        size.width * 0.5771863,
        size.height * 0.05190114
    )
    path.cubicTo(
        size.width * 0.5826996,
        size.height * 0.05418251,
        size.width * 0.5883080,
        size.height * 0.05598859,
        size.width * 0.5941065,
        size.height * 0.05722433
    )
    path.cubicTo(
        size.width * 0.5999049,
        size.height * 0.05846008,
        size.width * 0.6057985,
        size.height * 0.05912548,
        size.width * 0.6116920,
        size.height * 0.05922053
    )
    path.cubicTo(
        size.width * 0.6176806,
        size.height * 0.05922053,
        size.width * 0.6235741,
        size.height * 0.05874525,
        size.width * 0.6293726,
        size.height * 0.05760456
    )
    path.lineTo(size.width * 0.6425856, size.height * 0.05503802)
    path.cubicTo(
        size.width * 0.6790875,
        size.height * 0.04790875,
        size.width * 0.7161597,
        size.height * 0.06444867,
        size.width * 0.7352662,
        size.height * 0.09629278
    )
    path.lineTo(size.width * 0.7422053, size.height * 0.1077947)
    path.cubicTo(
        size.width * 0.7452471,
        size.height * 0.1129278,
        size.width * 0.7488593,
        size.height * 0.1176806,
        size.width * 0.7528517,
        size.height * 0.1220532
    )
    path.cubicTo(
        size.width * 0.7568441,
        size.height * 0.1264259,
        size.width * 0.7612167,
        size.height * 0.1303232,
        size.width * 0.7660646,
        size.height * 0.1338403
    )
    path.cubicTo(
        size.width * 0.7708175,
        size.height * 0.1372624,
        size.width * 0.7759506,
        size.height * 0.1403042,
        size.width * 0.7813688,
        size.height * 0.1427757
    )
    path.cubicTo(
        size.width * 0.7867871,
        size.height * 0.1452471,
        size.width * 0.7923954,
        size.height * 0.1471483,
        size.width * 0.7980989,
        size.height * 0.1484791
    )
    path.lineTo(size.width * 0.8112167, size.height * 0.1514259)
    path.cubicTo(
        size.width * 0.8474335,
        size.height * 0.1598859,
        size.width * 0.8746198,
        size.height * 0.1900190,
        size.width * 0.8791825,
        size.height * 0.2269011
    )
    path.lineTo(size.width * 0.8807985, size.height * 0.2402091)
    path.cubicTo(
        size.width * 0.8815589,
        size.height * 0.2461027,
        size.width * 0.8827947,
        size.height * 0.2519011,
        size.width * 0.8846958,
        size.height * 0.2575095
    )
    path.cubicTo(
        size.width * 0.8865970,
        size.height * 0.2631179,
        size.width * 0.8890684,
        size.height * 0.2685361,
        size.width * 0.8920152,
        size.height * 0.2736692
    )
    path.cubicTo(
        size.width * 0.8949620,
        size.height * 0.2788023,
        size.width * 0.8983840,
        size.height * 0.2836502,
        size.width * 0.9023764,
        size.height * 0.2881179
    )
    path.cubicTo(
        size.width * 0.9062738,
        size.height * 0.2924905,
        size.width * 0.9106464,
        size.height * 0.2965779,
        size.width * 0.9153992,
        size.height * 0.3000951
    )
    path.lineTo(size.width * 0.9260456, size.height * 0.3081749)
    path.cubicTo(
        size.width * 0.9557985,
        size.height * 0.3306084,
        size.width * 0.9682510,
        size.height * 0.3692015,
        size.width * 0.9574144,
        size.height * 0.4047529
    )
    path.lineTo(size.width * 0.9535171, size.height * 0.4175856)
    path.cubicTo(
        size.width * 0.9518061,
        size.height * 0.4231939,
        size.width * 0.9506654,
        size.height * 0.4290875,
        size.width * 0.9500951,
        size.height * 0.4349810
    )
    path.cubicTo(
        size.width * 0.9495247,
        size.height * 0.4408745,
        size.width * 0.9495247,
        size.height * 0.4467681,
        size.width * 0.9501901,
        size.height * 0.4526616
    )
    path.cubicTo(
        size.width * 0.9507605,
        size.height * 0.4585551,
        size.width * 0.9519962,
        size.height * 0.4643536,
        size.width * 0.9538023,
        size.height * 0.4700570
    )
    path.cubicTo(
        size.width * 0.9555133,
        size.height * 0.4756654,
        size.width * 0.9578897,
        size.height * 0.4811787,
        size.width * 0.9607414,
        size.height * 0.4863118
    )
    path.lineTo(size.width * 0.9673004, size.height * 0.4980989)
    path.cubicTo(
        size.width * 0.9852662,
        size.height * 0.5306084,
        size.width * 0.9809886,
        size.height * 0.5709125,
        size.width * 0.9566540,
        size.height * 0.5990494
    )
    path.lineTo(size.width * 0.9478137, size.height * 0.6091255)
    path.cubicTo(
        size.width * 0.9439163,
        size.height * 0.6135932,
        size.width * 0.9404943,
        size.height * 0.6184411,
        size.width * 0.9376426,
        size.height * 0.6236692
    )
    path.cubicTo(
        size.width * 0.9346958,
        size.height * 0.6288023,
        size.width * 0.9323194,
        size.height * 0.6342205,
        size.width * 0.9305133,
        size.height * 0.6398289
    )
    path.cubicTo(
        size.width * 0.9286122,
        size.height * 0.6455323,
        size.width * 0.9273764,
        size.height * 0.6513308,
        size.width * 0.9267110,
        size.height * 0.6572243
    )
    path.cubicTo(
        size.width * 0.9260456,
        size.height * 0.6631179,
        size.width * 0.9259506,
        size.height * 0.6690114,
        size.width * 0.9265209,
        size.height * 0.6749049
    )
    path.lineTo(size.width * 0.9276616, size.height * 0.6883080)
    path.cubicTo(
        size.width * 0.9308935,
        size.height * 0.7253802,
        size.width * 0.9105513,
        size.height * 0.7604563,
        size.width * 0.8769011,
        size.height * 0.7762357
    )
    path.lineTo(size.width * 0.8647338, size.height * 0.7818441)
    path.cubicTo(
        size.width * 0.8593156,
        size.height * 0.7844106,
        size.width * 0.8542776,
        size.height * 0.7874525,
        size.width * 0.8495247,
        size.height * 0.7909696
    )
    path.cubicTo(
        size.width * 0.8447719,
        size.height * 0.7944867,
        size.width * 0.8403042,
        size.height * 0.7984791,
        size.width * 0.8364068,
        size.height * 0.8028517
    )
    path.cubicTo(
        size.width * 0.8324144,
        size.height * 0.8072243,
        size.width * 0.8288973,
        size.height * 0.8120722,
        size.width * 0.8258555,
        size.height * 0.8172053
    )
    path.cubicTo(
        size.width * 0.8229087,
        size.height * 0.8222433,
        size.width * 0.8204373,
        size.height * 0.8276616,
        size.width * 0.8184411,
        size.height * 0.8332700
    )
    path.lineTo(size.width * 0.8140684, size.height * 0.8459125)
    path.cubicTo(
        size.width * 0.8019962,
        size.height * 0.8810837,
        size.width * 0.7691065,
        size.height * 0.9049430,
        size.width * 0.7319392,
        size.height * 0.9056084
    )
    path.lineTo(size.width * 0.7185361, size.height * 0.9058935)
    path.cubicTo(
        size.width * 0.7126426,
        size.height * 0.9059886,
        size.width * 0.7067490,
        size.height * 0.9066540,
        size.width * 0.7009506,
        size.height * 0.9079848
    )
    path.cubicTo(
        size.width * 0.6951521,
        size.height * 0.9092205,
        size.width * 0.6895437,
        size.height * 0.9111217,
        size.width * 0.6841255,
        size.height * 0.9134981
    )
    path.cubicTo(
        size.width * 0.6787072,
        size.height * 0.9158745,
        size.width * 0.6735741,
        size.height * 0.9188213,
        size.width * 0.6687262,
        size.height * 0.9223384
    )
    path.cubicTo(
        size.width * 0.6638783,
        size.height * 0.9257605,
        size.width * 0.6594106,
        size.height * 0.9296578,
        size.width * 0.6554183,
        size.height * 0.9339354
    )
    path.lineTo(size.width * 0.6461977, size.height * 0.9438213)
    path.cubicTo(
        size.width * 0.6208175,
        size.height * 0.9710076,
        size.width * 0.5811787,
        size.height * 0.9793726,
        size.width * 0.5469582,
        size.height * 0.9649240
    )
    path.lineTo(size.width * 0.5346008, size.height * 0.9596958)
    path.cubicTo(
        size.width * 0.5290875,
        size.height * 0.9573194,
        size.width * 0.5234791,
        size.height * 0.9556084,
        size.width * 0.5176806,
        size.height * 0.9543726
    )
    path.cubicTo(
        size.width * 0.5117871,
        size.height * 0.9532319,
        size.width * 0.5058935,
        size.height * 0.9526616,
        size.width * 0.5000000,
        size.height * 0.9526616
    )
    path.cubicTo(
        size.width * 0.4941065,
        size.height * 0.9526616,
        size.width * 0.4882129,
        size.height * 0.9532319,
        size.width * 0.4823194,
        size.height * 0.9543726
    )
    path.cubicTo(
        size.width * 0.4765209,
        size.height * 0.9556084,
        size.width * 0.4709125,
        size.height * 0.9573194,
        size.width * 0.4653992,
        size.height * 0.9596958
    )
    path.lineTo(size.width * 0.4530418, size.height * 0.9649240)
    path.cubicTo(
        size.width * 0.4188213,
        size.height * 0.9793726,
        size.width * 0.3791825,
        size.height * 0.9710076,
        size.width * 0.3538023,
        size.height * 0.9438213
    )
    path.lineTo(size.width * 0.3445817, size.height * 0.9339354)
    path.cubicTo(
        size.width * 0.3405894,
        size.height * 0.9296578,
        size.width * 0.3361217,
        size.height * 0.9257605,
        size.width * 0.3312738,
        size.height * 0.9223384
    )
    path.cubicTo(
        size.width * 0.3264259,
        size.height * 0.9188213,
        size.width * 0.3212928,
        size.height * 0.9158745,
        size.width * 0.3158745,
        size.height * 0.9134981
    )
    path.cubicTo(
        size.width * 0.3104563,
        size.height * 0.9111217,
        size.width * 0.3048479,
        size.height * 0.9092205,
        size.width * 0.2990494,
        size.height * 0.9079848
    )
    path.cubicTo(
        size.width * 0.2932510,
        size.height * 0.9066540,
        size.width * 0.2873574,
        size.height * 0.9059886,
        size.width * 0.2814639,
        size.height * 0.9058935
    )
    path.lineTo(size.width * 0.2680608, size.height * 0.9056084)
    path.cubicTo(
        size.width * 0.2308935,
        size.height * 0.9049430,
        size.width * 0.1980038,
        size.height * 0.8810837,
        size.width * 0.1859316,
        size.height * 0.8459125
    )
    path.lineTo(size.width * 0.1815589, size.height * 0.8332700)
    path.cubicTo(
        size.width * 0.1795627,
        size.height * 0.8276616,
        size.width * 0.1770913,
        size.height * 0.8222433,
        size.width * 0.1741445,
        size.height * 0.8172053
    )
    path.cubicTo(
        size.width * 0.1711027,
        size.height * 0.8120722,
        size.width * 0.1675856,
        size.height * 0.8072243,
        size.width * 0.1635932,
        size.height * 0.8028517
    )
    path.cubicTo(
        size.width * 0.1596958,
        size.height * 0.7984791,
        size.width * 0.1552281,
        size.height * 0.7944867,
        size.width * 0.1504753,
        size.height * 0.7909696
    )
    path.cubicTo(
        size.width * 0.1457224,
        size.height * 0.7874525,
        size.width * 0.1406844,
        size.height * 0.7844106,
        size.width * 0.1352662,
        size.height * 0.7818441
    )
    path.lineTo(size.width * 0.1230989, size.height * 0.7762357)
    path.cubicTo(
        size.width * 0.08944867,
        size.height * 0.7604563,
        size.width * 0.06910646,
        size.height * 0.7253802,
        size.width * 0.07233840,
        size.height * 0.6883080
    )
    path.lineTo(size.width * 0.07347909, size.height * 0.6749049)
    path.cubicTo(
        size.width * 0.07404943,
        size.height * 0.6690114,
        size.width * 0.07395437,
        size.height * 0.6631179,
        size.width * 0.07328897,
        size.height * 0.6572243
    )
    path.cubicTo(
        size.width * 0.07262357,
        size.height * 0.6513308,
        size.width * 0.07138783,
        size.height * 0.6455323,
        size.width * 0.06948669,
        size.height * 0.6398289
    )
    path.cubicTo(
        size.width * 0.06768061,
        size.height * 0.6342205,
        size.width * 0.06530418,
        size.height * 0.6288023,
        size.width * 0.06235741,
        size.height * 0.6236692
    )
    path.cubicTo(
        size.width * 0.05950570,
        size.height * 0.6184411,
        size.width * 0.05608365,
        size.height * 0.6135932,
        size.width * 0.05218631,
        size.height * 0.6091255
    )
    path.lineTo(size.width * 0.04334601, size.height * 0.5990494)
    path.cubicTo(
        size.width * 0.01901141,
        size.height * 0.5709125,
        size.width * 0.01473384,
        size.height * 0.5306084,
        size.width * 0.03279468,
        size.height * 0.4980989
    )
    path.lineTo(size.width * 0.03925856, size.height * 0.4863118)
    path.cubicTo(
        size.width * 0.04211027,
        size.height * 0.4811787,
        size.width * 0.04448669,
        size.height * 0.4756654,
        size.width * 0.04619772,
        size.height * 0.4700570
    )
    path.cubicTo(
        size.width * 0.04800380,
        size.height * 0.4643536,
        size.width * 0.04923954,
        size.height * 0.4585551,
        size.width * 0.04980989,
        size.height * 0.4526616
    )
    path.cubicTo(
        size.width * 0.05047529,
        size.height * 0.4467681,
        size.width * 0.05047529,
        size.height * 0.4408745,
        size.width * 0.04990494,
        size.height * 0.4349810
    )
    path.cubicTo(
        size.width * 0.04933460,
        size.height * 0.4290875,
        size.width * 0.04819392,
        size.height * 0.4231939,
        size.width * 0.04648289,
        size.height * 0.4175856
    )
    path.lineTo(size.width * 0.04258555, size.height * 0.4047529)
    path.cubicTo(
        size.width * 0.03165399,
        size.height * 0.3692015,
        size.width * 0.04420152,
        size.height * 0.3306084,
        size.width * 0.07395437,
        size.height * 0.3081749
    )
    path.lineTo(size.width * 0.08460076, size.height * 0.3000951)
    path.cubicTo(
        size.width * 0.08935361,
        size.height * 0.2965779,
        size.width * 0.09372624,
        size.height * 0.2924905,
        size.width * 0.09762357,
        size.height * 0.2881179
    )
    path.cubicTo(
        size.width * 0.1016160,
        size.height * 0.2836502,
        size.width * 0.1050380,
        size.height * 0.2788023,
        size.width * 0.1079848,
        size.height * 0.2736692
    )
    path.cubicTo(
        size.width * 0.1109316,
        size.height * 0.2685361,
        size.width * 0.1134030,
        size.height * 0.2631179,
        size.width * 0.1153042,
        size.height * 0.2575095
    )
    path.cubicTo(
        size.width * 0.1172053,
        size.height * 0.2519011,
        size.width * 0.1184411,
        size.height * 0.2461027,
        size.width * 0.1192015,
        size.height * 0.2402091
    )
    path.lineTo(size.width * 0.1208175, size.height * 0.2269011)
    path.cubicTo(
        size.width * 0.1253802,
        size.height * 0.1900190,
        size.width * 0.1525665,
        size.height * 0.1598859,
        size.width * 0.1887833,
        size.height * 0.1514259
    )
    path.lineTo(size.width * 0.2019011, size.height * 0.1484791)
    path.cubicTo(
        size.width * 0.2076046,
        size.height * 0.1471483,
        size.width * 0.2132129,
        size.height * 0.1452471,
        size.width * 0.2186312,
        size.height * 0.1427757
    )
    path.cubicTo(
        size.width * 0.2240494,
        size.height * 0.1403042,
        size.width * 0.2291825,
        size.height * 0.1372624,
        size.width * 0.2339354,
        size.height * 0.1338403
    )
    path.cubicTo(
        size.width * 0.2387833,
        size.height * 0.1303232,
        size.width * 0.2431559,
        size.height * 0.1264259,
        size.width * 0.2471483,
        size.height * 0.1220532
    )
    path.cubicTo(
        size.width * 0.2511407,
        size.height * 0.1176806,
        size.width * 0.2547529,
        size.height * 0.1129278,
        size.width * 0.2577947,
        size.height * 0.1077947
    )
    path.lineTo(size.width * 0.2647338, size.height * 0.09629278)
    path.cubicTo(
        size.width * 0.2838403,
        size.height * 0.06444867,
        size.width * 0.3209125,
        size.height * 0.04790875,
        size.width * 0.3574144,
        size.height * 0.05503802
    )
    path.lineTo(size.width * 0.3706274, size.height * 0.05760456)
    path.cubicTo(
        size.width * 0.3764259,
        size.height * 0.05874525,
        size.width * 0.3823194,
        size.height * 0.05922053,
        size.width * 0.3883080,
        size.height * 0.05922053
    )
    path.cubicTo(
        size.width * 0.3942015,
        size.height * 0.05912548,
        size.width * 0.4000951,
        size.height * 0.05846008,
        size.width * 0.4058935,
        size.height * 0.05722433
    )
    path.cubicTo(
        size.width * 0.4116920,
        size.height * 0.05598859,
        size.width * 0.4173004,
        size.height * 0.05418251,
        size.width * 0.4228137,
        size.height * 0.05190114
    )
    path.cubicTo(
        size.width * 0.4282319,
        size.height * 0.04952471,
        size.width * 0.4333650,
        size.height * 0.04657795,
        size.width * 0.4382129,
        size.height * 0.04315589
    )
    return path
}

private fun Path.moveTo(x: Double, y: Double) {
    this.moveTo(x.toFloat(), y.toFloat())
}

private fun Path.lineTo(x: Any, y: Any) {
    this.lineTo(x.toFloat(), y.toFloat())
}

private fun Path.cubicTo(x1: Any, y1: Any, x2: Any, y2: Any, x3: Any, y3: Any) {
    this.cubicTo(x1.toFloat(), y1.toFloat(), x2.toFloat(), y2.toFloat(), x3.toFloat(), y3.toFloat())
}

private fun Any.toFloat(): Float {
    return if (this is Float) {
        this
    } else {
        val double = this as Double
        double.toFloat()
    }
}
