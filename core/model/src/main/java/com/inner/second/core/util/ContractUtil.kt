package com.inner.second.core.util

import com.inner.second.core.model.ContractFormInput
import com.inner.second.core.model.ContractKey
import com.inner.second.core.model.ContractType
import com.inner.second.core.model.DateDuration
import com.inner.second.core.model.response.User
import java.text.NumberFormat
import java.time.LocalDateTime

fun ContractType.getHtmlForm(
    map: Map<String, Any>,
    user: User = User.dummy(),
): String {
    val title = map[ContractFormInput.ContractTitle.key] as String
    val duration = map[ContractFormInput.DateDuration.key] as DateDuration
    val opponentName = map[ContractFormInput.OpponentName.key]

    val content = when (this) {
        is ContractType.IOU -> {
            val itemType = buildList {
                listOf(
                    ContractFormInput.ItemName,
                    ContractFormInput.Price
                )
                    .forEach {
                        if (map[it.key] != null) {
                            add(it)
                        }
                    }
            }
                .firstOrNull()
            val item = itemType?.let { input ->
                when (input) {
                    is ContractFormInput.Price -> {
                        NumberFormat.getInstance().format(map[input.key] as Long) + "원"
                    }
                    else -> map[input.key]
                }
            }

            """
                <div class="contract-description">
                    <p><b><u>${opponentName}</u></b>(이하 '채무자'라고 합니다)는 <b><u>${user.name}</u></b>(이하 '채권자'라고 합니다)로부터 아래와 같이 차용하였음을 확인하고, 변제할 것을 확약합니다.</p>
                </div>

                <div class="contract-under">
                    <p><b>- 아 래 -</b></p>
                </div>
                
                <div class="contract-item">
                    ${itemType?.title} : $item
                    </br>
                    기간 : ${duration.startDate.toFormattedString()} ~ ${duration.endDate.toFormattedString()}
                </div>
            """
        }

        is ContractType.New -> {
            val multipleDescription = map[ContractFormInput.MultipleDescription.key] as List<String>
            """
                <div class="contract-description">
                    ${
                        multipleDescription.joinToString(separator = "</br>", prefix = "<p>", postfix = "<p>")
                    }
                </div>
            """
        }
    }
        .trimIndent()

    return """
        <!DOCTYPE html>
        <html>
        <head>
        <meta charset="UTF-8">
        <title>${title}</title>
        <style>
        body {
            font-family: '맑은 고딕', sans-serif;
            line-height: 1.6;
            margin: 20px;
            font-size: 14px; 
        }
        
        .contract-title {
            font-size: 24px;
            font-weight: bold;
            text-align: center;
            margin-bottom: 20px;
        }

        .contract-description {
            margin-bottom: 30px;
        }
        
        .contract-item {
            font-weight: bold;
            margin-bottom: 50px;
        }
        
        .contract-date {
            font-weight: bold;
            text-align: center;
            margin-bottom: 30px;
        }
        
        .contract-under {
           text-align: center; 
           margin-bottom: 20px;
           margin-top: 20px;
        }
        
        .signature-container {
            display: flex;
            justify-content: flex-start;
        }
        
        .signature-container > div {
            width: fit-content;
            margin-right: 16px;
        }
        
        .signature-container img {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%; 
            height: 100%; 
            object-fit: contain; 
            transform: scale(2);
        }
       
        </style>
        </head>
        <body>

        <div class="contract-title">
            $title
        </div>
        
        $content

        <div class="contract-date">
            ${LocalDateTime.now().toFormattedStringInKorean()}
        </div>
    
        <div>
            <div class="signature-container">
                <div>
                    <b>${ if (this == ContractType.IOU) "채권자" else "작성인" } : <u>${user.name}</u></b>
                </div>
                <div style="position: relative; display: inline-block;">
                    <span>(서명 또는 인)</span>
                    ${
                        if (map.containsKey(ContractKey.Signature.name)) {
                            "<img src=\"data:image/png;base64, ${map[ContractKey.Signature.name] as String}\">"
                        } else {
                            ""
                        }
                    }               
                </div>
            </div>    
            <div>
                <b>주소 : <u>${user.address}</u></b>
            </div>
            <div>
                <b>전화번호 : <u>${user.phoneNumber}</u></b>
            </div>
        </div>
    
        </br>
        
        <div>
            <div class="signature-container">
                <div>
                    <b>${ if (this == ContractType.IOU) "채무자" else "수신인" } : <u>$opponentName</u></b>
                </div>
                <div style="position: relative; display: inline-block;">
                    <span>(서명 또는 인)</span>
                </div>
            </div>
            <div>
                <b>주소 : <u></u></b>
            </div>
            <div>
                <b>전화번호 : <u></u></b>
            </div>
        </div>

        </body>
        </html>
    """.trimIndent()
}

object ContractUtil {
    fun contractDummy(): String {
        return """
                    <!DOCTYPE html>
                    <html>
                    <head>
                    <meta charset="UTF-8">
                    <title>금전계약서</title>
                    <style>
                    body {
                        font-family: '맑은 고딕', sans-serif;
                        line-height: 1.6;
                        margin: 20px;
                        font-size: 14px; 
                    }
                    
                    .contract-title {
                        font-size: 24px;
                        font-weight: bold;
                        text-align: center;
                        margin-bottom: 20px;
                    }

                    .contract-description {
                        margin-bottom: 30px;
                    }
                    
                    .contract-item {
                        font-weight: bold;
                        margin-bottom: 50px;
                    }
                    
                    .contract-date {
                        font-weight: bold;
                        text-align: center;
                        margin-bottom: 30px;
                    }
                    
                    .contract-under {
                       text-align: center; 
                       margin-bottom: 20px;
                       margin-top: 20px;
                    }
                    
                    .signature-container {
                        display: flex;
                        justify-content: flex-start;
                    }
                    
                    .signature-container > div {
                        width: fit-content;
                        margin-right: 16px;
                    }
                    
                    .signature-container img {
                        position: absolute;
                        top: 0;
                        left: 0;
                        width: 100%; 
                        height: 100%; 
                        object-fit: contain; 
                        transform: scale(2);
                    }
                   
                    </style>
                    </head>
                    <body>

                    <div class="contract-title">
                        금전계약서
                    </div>
                    
                    <div class="contract-description">
                <p><b><u>김한슬</u></b>(이하 '채무자'라고 합니다)는 <b><u>김경택</u></b>(이하 '채권자'라고 합니다)으로부터 아래와 같이 차용하였음을 확인하고, 변제할 것을 확약합니다.</p>
            </div>

            <div class="contract-under">
                <p><b>- 아 래 -</b></p>
            </div>

            <div class="contract-item">
                빌려줄 금액 : 50,000원
                </br>
                기간 : 2024.10.09 ~ 2024.11.09
            </div>

                    <div class="contract-date">
                        2024년 12월 08일
                    </div>
                
                    <div>
                        <div class="signature-container">
                            <div>
                                <b>채권자 : <u>김경택</u></b>
                            </div>
                            <div style="position: relative; display: inline-block;">
                                <span>(서명 또는 인)</span>
                                <img src="data:image/png;base64, iVBORw0KGgoAAAANSUhEUgAAA94AAAIzCAYAAAD2wMHDAAAAAXNSR0IArs4c6QAAAARzQklUCAgI
            CHwIZIgAACAASURBVHic7d179OV3Xd/7Zy7QgAF+WhF0eZpB6wUrMhSreImOtlaLpUZdq2I56KjU
            Wqwa6qVqORDtUmPVRY6XwmprGUQ9VkVSaI/ReiQKioqHDNgiIjQDIoSLzARDriQ5f3xnDgGTyfx+
            39tv//bjsdZnzazA3vP+fPfe3+9+7c/n+/kUAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
            AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
            AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
            AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
            AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
            AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAwN6ct3YBABwI
            h6tLqsuqJ1QfW118L/+/a+/x9wurj6x2qouq91dvrF5W/WJ1fMZ6AQAWI3gDsBuHGgL2kdN/P3y6
            zeXahgB+bfWa6sSM/xYAAAAs6qLq/6h+r3p3dfc+aHdW11VPnLHfAAAAMJvHVt9Zvam6q/WD9tna
            HzeMvAMAAMC+9fDqq6vnV3/e+mF6L+1lCeAAAADsI19U/UjDlO21Q/PUI+BPm/A4AQAAwDl5dPVt
            1X+rbmr9gDx3e1fDAnAAAAAwi49o86ePj213Vc8ceyABAACg6oHV51c/UL2qYeXvtYPvfmlPH3Fc
            AQAA2GLbNn18TDu6x2MMAADAFjmzp/a11anWD7Ob1oRvAAAA7tWHVb9evb/1w+umN+EbAACAD/LV
            1W2tH1gPUhO+AQAA6HD1stYPqQe1Cd8AAABbaqdhC7C1g+k2NOEbAFjMeWsXAEBVz64ubwjfm+rN
            1YmGBeBO3OPvZ+w0jOZ/qItP//eLqyec/vvDZq108HXVsQX+HQAAAFZ0WXV9648A76bdWv1+dVXD
            jwVHmv4Hg0PVd1TXNe/Ccka+AQAADqhNuo/7zdUvNQThT5jjYNyPneqK5ttGTfgGAAA4QC5pGMVd
            O0yfrd1Y/Ur1jafr3S92qiurOxO+AQAAuBfPaH/ux31X9erqB6tLqwfMdQAmckl1Q9Mfh2ct2QkA
            AACms1P9bOsH7Hu291S/2LDA2CPm6/psdqrjTX9cnrFkJwAAABjvcPtjavmdDQuifV/1WdUFc3Z6
            IXOE77saRv0BAADYAN/WumH7huoF1ZOrj5y5r2uZI3z/5aI9AAAAYNd2qhe3fNC+vWHf7O9uGGk/
            b+6O7hNzhO8rFu0BAAAA5+xIdbLlwvZt1Yuqr6weukD/9qs5wvfhRXsAAADA/XpOywXuv6ieuUy3
            NsbU4fu6ZcsHAADgvhxquQXUTjVMg95ZpGebZ+rwbco5AADAyo623NTyqxtCPmc3dfg25RwAAGAF
            Sy6gdrzh3nHO3ZTh25RzAACAhR2urm+ZaeVHF+rTQbRT/XnTvBbPW7h2AACArfXjLTPK7T7uaexU
            tzTNa3LpwrUDAABslcdU72z+wP326tMW6tO2ONw0r83JpQsHAADYFn+ven/zBu7bqu9fqkNb6Iqm
            eZ2etXThAAAAB92/ru5q3tB9PCtnL2GKxdbevnjVAAAAB9RDq5c2/9Tyq3Iv91KmmnJuhXkAAICR
            /lb1xuYN3Keqy5bqEP+/KaacX7141QAAAAfIV1U3N2/ovjaj3GuaYsr5ocWrBgAA2HAXVM9p/qnl
            ly/VIe7TFFPOjy1eNQAAwAZ7ePXbzRu4T2QBtf3k6ox6AwAALOIJ1duaN3Qfy9Ty/WanurNxr+sV
            i1cNAACwYf5FdXvzBW4LqO1vVzbu9b09P6gAAADcqwdVv9C8o9yvyVTk/W6n8a/zTy5eNQAAwD53
            SfU/mjd0/8hivWGsY417rW9evmSAA+NQ9fnVl1Y/Xv3m6faD1aetWBcAMMI/qE42X+C+o3rqYr1h
            Coca/7pfunjVAJthp/qhhjD9rur66n917ufXd55+vMVJAWADnNewENZdzRe6r6seulSHmNTYUe/f
            XL5kgH3tsuq1TXudvb5h288vW7AfAMA52qmuab7AfWf1PYv1hjkcadx74C+XLxlg3zlcPb9hYdG5
            rrln2u0NP3j/+0V6BgCc1WMb9s+e68L/7uoLF+sNc3p9494LVq8HttGh6tkNo9Fzh+37anckgAPA
            ar6qurX5LvR/WH3sYr1hbs9s3Pvh2PIlA6xip/qB6k9aL2zfW/uz7CYCAIu5sGGLpzkv7j9TPXCp
            DrGYMXu6n8ye3sDB9rHVK1o/YN9fe/ZcBwAAGHx0wy/ec13Mb63+6WK9YWn/qXHvj6PLlwwwu7/R
            +PPj0u26rIQOALO4tLqt+S7ib60ev1hvWMPhxr1HXr18yQCzeVRD4L6j9YP0XpvRbwCY0KUNq4vP
            deH+f6q/vlhvWNPYxfhMNwc23SdWP9f6oXmq9s7qiZMeIQDYQoebdxG1H6rOX6w3rO2Kxr1frly+
            ZIBJfFL1fzXvD9lrtv843aECgO2yUx1vngv0jdU/XK4r7BOHGve+ef3yJQOM8inVL1V3tX44Fr4B
            YJ/ZaVg8ZY4L8+urj1+uK+wzb2zv753bVqgXYC8eU7247Qjc92xPnuLgAcA2mDN0/1J18XJdYR8a
            ux2dlXSB/exx1UtbPwCv1W7Jft8AcL/mCt13VP9ywX6wf42dbn7F8iUD3K9Pr3619YPvfmjXZTFM
            ADira5v+AvzOhpXR4Yz3tPf30ytWqBfgvjyhuqb1w+59teMN583Xn/77nAum3rM9f8xBBYCD7LKm
            v/C+svroJTvBRriyce8rIynA2r6welnrB+sz7Y0Nt/I8ufuf6n1ZdVXjt3i8v3b5uRxIANgmO9XJ
            pr3g/lR14ZKdYGMcadx76ynLlwxQ1d9vGEFeO2jf3RCcr2rc2heHq2PV7TPVaMYbANzD2CD0oe2p
            y5bPBjrV3t9fb1yhXmC7PbH6vdYP23c3BOXLJu7fTvXcGWp9f3XJxLUCwMa6vGkusHdV/3jh2tlM
            V7f399kdK9QLbKcnVa9u/bD9rurpzX+rzZGmn4L+pzPXDAAb44rGX1hvbVhkBs7F2B97ph7tAbin
            r2i+rTV30443jLYvbYrvBfdsz1y2fADYn4427oJ6Y/ZXZnfGbit2bPmSgQPu/IZZW3/UumH7roZZ
            QY+Zt7v363B1U9P06X0L1w4A+9Lhxl1QjT6yF2O2tTm5Qr3AwXR+9U+q17V+4P7l6lPm7e6u7DRu
            TY57tqML1w4A+9Je7+lyIWWvfj0/+ADruaD6mupPWjdw31n9QvVJ83Z3zw43zGwb28/rly4cAPaj
            vYx6C92MMXamxTXLlwwcABdW39CwQ8Kagfv91Qurvzlvdycx9pY03xsA4B52s5iKiydTeHfjvrQC
            nKsHNOx7Pea8M0W7o3p+9ah5uzu5qxrf95ctXjUA7FOXdfb7uY5nITWmM/aLnJVygXPxYw0/1q0Z
            uO+u/kPD4pKb6vrGH4Mji1cNAPvUTsN2T8cagva1DQHpaPPvIcp2GTvd/BXLlwxskEPVW1o3bN9W
            Pbf62Jn7uoSdhkXgxhwPo94AACsYM+3zlvwYBNy7pzZu94Sx7ZbqJ6qPnrujC/uRjHoDAGycX27c
            FzjrDQD3tFO9qPUC983Vc6pHzN3RlexU723cMfqzxasGANhylzbuC5xpi8AZn1e9vfVGuH+4evjs
            vVzfbhZjva/2tMWrBgDYcmOng5puDtvtAdWPNv7+472091Y/VH3E7L3cP3Y6+0KsRr0BAPah72/c
            F7grly8Z2CceXb225QP3qYZz17b+8DfFqLd7vQEAFnSocV/e3rR8ycA+8K0NU7yXDNzvqZ5VPWyB
            /u1nU4x6u1UIAGBhxxv3BQ7YHh9V/VrLBu53V99bPWSB/m0Ko94AABvm8sZ9ebts+ZKBFTypcdsQ
            7ra9o/qu6sOW6NyG2aluz6g3AMDGGDvd/NeXLxlY0IOr/9hygftd1TOqBy3RuQ12dUa9AQA2ypj7
            Be9sexc5goPu7zSs5bBE4H5rw73jFy3Ss813ScP5d8wxv3rxqgEAtthPNu7L28uXLxmY0QUNC5nd
            0fyB+53V0xu2JmN3ntf4439o8aoBALbU4ab5Av3a6tXVK6vfrJ5bfVv1+ct1BRjpkuoPmj9w396w
            LRh7t1O9r3Gvw7HFqwYA2GK3Nv8X7Wcv1htgL45W723+c8GLqosX6tNBN8UK55+2eNUAAFtq7LZi
            59quaxhhB/aPneolzf/5P1l9xUJ92hZT7Ot9fPGqAQC21NNaJnifCd/A/vDE6sbm/9z/VvXIhfq0
            ba5q/OtzyeJVAwBsqSWmm59pVyzUJ+C+/XbLfN6/fakObamx20LeXf344lUDAGypZ7Vc8L57oT4B
            f9VO9efN/xl/XfWYhfq07Y417rV6y/IlAwBsr3e0XPA+slCfgA840vyzW+5qmP78wIX6xPhR79uX
            LxkAYHtd3nLB+/KF+gQMnt38n+u3V393qQ7xQcaOelv4EgBgQdcmeMNBslO9rPk/0y85/W+xjrGj
            3s7JAAALG7s9zbk0U81hfoer65v3s3xz9Q1LdYizuqG9v45Xr1AvAMBW26le0bxf1oF5fVvz/4D2
            B9XHLdUh7teV7f21PLlCvQAAVL/RPF/WbScG89mpXty8gfv9DZ/jCxbqE+fmSONe10PLlwwAQI0b
            Qbm3dnzZ8mGrXFq9q3lD94nq7yzVIXZlp3Gv7dHlSwYA4IwnVjc1Tei2ci7M4z80b+C+u/pP1cVL
            dYg9Od7eX99jK9QLAMCHeHLDFNY3VW+t3te5f6EzvRzmsVO9vHkD943Vk5bqEKNc1d5f5+tXqJcN
            dd7aBQDAFjpcfWz1JdWnNqyKfqph5OV4wxZlwPR2qtdUf2PGf+N1DXtz3zDjv8F0Lmv4gXSvHtVw
            OwEAAMDWO9ywEvWcI90/v1hvmMrY+7y/Y/mSAQAA9p+jzRu4b83U8k025j7vV65QLwAAwL7ynOYN
            3dc2jJqyuY6199f/HSvUCwAAsC/sVC9r3tBtEcSD4Tva+3vgvSvUCwAAsLrD1XXNF7hPVUcW6w1z
            ++L2/l543wr1AgAArOqy5l1E7Xh1aLHesITPaO/vhz9foV4AAIDVfFvzTi2/armusKAj7f098Xsr
            1AsAALC4ner5zRe4b2oYSedg+gft/b1xzQr1soEuXLsAAAAY4cwiaodnev4/qz6vOjHT87O+B414
            7M2TVcGBJngDALCpDjeE7rm28/r96gkzPTf7x5jgfctkVXCgnb92AQAAsAdHG1Yunyt0f2dC97Z4
            8IjHCt6cEyPeAABsmudUl8/03Dc2LLZ1fKbnZ/8x4s3sBG8AADbFTvXi5ttD+zWnn/vUTM/P/uQe
            b2ZnqjkAAJviOc0Xul+Q0L2tTDVndoI3AACb4IqG+7rn8IzTzy10bydTzZmdqeYAAGyCZ8/wnDc2
            BO6rZ3huNofgzeyMeAMAsN9dNsNznrmfW+jGPd7Mzog3AAD73dTB+7dOP6ep5ZR7vFmAEW8AAPa7
            ExM+1/+ZRdT4YKaaMzsj3gAAbIuvq46tXQT7jqnmzE7wBgBgvxs7On1jwyj38Qlq4eAx4g0AANAw
            3fzuPbTj1c4K9bI5XtXe3lt3V5+xQr1sIPd4AwCwCa7Yw2NekPu5uX9GvAEAAE471rmPRF6+Uo1s
            nje19xHvj1+hXgAAgFld3tmD0ImGUW44V29v78H7Y1aoFwAAYHaHGgL41X3gPu6rq6NrFsXGOtXe
            g/eHr1AvAAAAbJTb2nvwvmiFetlA561dAAAAwIruHvFYeYpzYlVzAABgW1084rE3T1YFB57gDQAA
            bCtbibEIwRsAANhWY4L3nZNVwYEneAMAANtqTPC+YLIqOPAEbwAAYFt9/IjHylKcM28WAABgWz10
            xGNlKc6ZNwsAALCtbhjx2L+crAoOPMEbAADYVkdGPPZPJ6uCA0/wBgAA2L3fWbsANofgDQAAsHu2
            E+OcCd4AAMC2Ojzisacmq4IDT/AGAAC21c6Ixx6frAoOPMEbAAAAZiR4AwAA2+rQ2gWwHc5buwAA
            AICV3D3isbIU58yINwAAAMxI8AYAALbRmIXVYFcEbwAAYBt97ojHnpysCraC4A0AAGyji0c8Vo5i
            V7xhAACAbXTDiMfeNFkVbAXBGwAAYHdOrV0Am0XwBgAA2B3Bm10RvAEAAHbn/WsXwGYRvAEAAGBG
            gjcAAADMSPAGAAC20ZERj712sirYCoI3AAAAzEjwBgAAgBkJ3gAAwDa6aKXHsoUEbwAAYBs9cqXH
            soUEbwAAYBsdWumxbCHBGwAA2EY3rPRYtpDgDQAAbKNTIx4reLMrgjcAALCNxoTnMaGdLSR4AwAA
            wIwEbwAAAJiR4A0AAAAzErwBAABgRoI3AAAAzEjwBgAAgBkJ3gAAADAjwRsAAABmJHgDAADAjARv
            AAAAmJHgDQAAADMSvAEAAGBGgjcAAADM6MK1CwAA9o2HVV9dPa66pDpV/Xb1P/fwXL81YV0AAACw
            kT65Olr9h+od1d0LtFuqV1afu0D/AM7mWHs/lx1boV4AAPa5B1afVX1H9eKWC9pna39UfdmcnQY4
            i6vb+/nr6hXqBQBgn/mI6knVD1cvb/2QfbZ2ffW11c4sRwLg3l3T3s9b16xQLxvMPd4AcDB8UvU5
            92iftG45u3KoYdrmqeqq6gXViVUrArbBDSs9FgCADfDXGu6R/lfVS6p3tf6o9dTt+dXhqQ4YwL24
            or2fo65YoV42mBFvANj//nr1edVnNwTuJ6xbziKOnm7XNoyGv2DdcgBg7wRvANhfzqse3QdPG/+b
            q1a0riOn21UNq6//YMOUdADYGII3AKzrouozG0azP+f0nx++akX70071ndW3N0xDf9q65QDAuRO8
            AWBZH1Vd2gdGsx9XPWDVijbL+dU3VF9UPTaj38DejdlJ4aLJqmArCN4AMJ/zqk/tAyH7s6uPW7Wi
            g+NvVL9T/a21CwE21pjg/cmTVcFWELwBYDoPalj47EzQ/qzqYatWdLB9SvW86pvWLgTYOo9cuwA2
            i+ANAHv3MQ2rjJ8J2o/NtXVp/6y6prp67UKAjTNmxHvMYwEAOIuPqX6q+uPqltbf61ob2sns+Q3s
            3tPa+3nnvSvUCwBwYD2oekr1R60fMLX7btdlBArYnUONO+8cWr5kNtV5axcAAPvUF1ZfX3159eCV
            a1na+6u3VSeq97X7/u80TLtf2rXVF6zw7wKb6+4Rj/2ChvMOAAC78InVD1Vvaf0R3CXbn1X/ubq8
            +ozRR/Gv2jn93CcW6MtVM9QPHFzX5nwDADC7j6i+pXpV6wfgJdrt1e9Xz6n+ccN960s62rgvuufS
            ji7WG2DTXdXezzVvWaFeAICN8k+q/9r6QXju9o6GFb+/q2EF9r82xcGbwJHqWPP02WJrwLm6vL2f
            a+5YoV4AgH3vcxr2fT7Z+oF4jnZndbx6bvXU6uOnOWyzOtQw4nSqaY/F9VlsDbh/Rxp3rrls+ZIB
            APafS6pnV29s/WA8dTtZ/Wr1rOrvVRdPdMzWsNMwRXzKAP7yRXsAbKox55ljK9QLALAvPKT6xuoV
            rR+Op2p3Va+v/lPD3rN/q4O7O8nvN91xe+nCtQOb56bG/QAKALA1LqyeVP1idWvrB+Wx7X0Ni5D9
            QPUPGxaB2xY7DVPmpzqWz1y2fGDD/HrjzjGmmwMAB96nN9zT/J7WD8tj2vXVz1f/ovrb1QVTHqQN
            dLjppp3f2XDLAcC9ubRx55iXLF8yAMD8PqX6meqG1g/Me2m3Vr9b/Vj1ldUjpz08B8ZlTXfMjy9c
            O7BZ3ta4H/cs5ggAHBifUL229YPzbtvbqxdV3159dvtnS69NcEXTvQ6HFq4d2Bxj9vO+O4usAQAH
            wIdXv9D6Afpc2vurV1c/2bBX+KNmOB7bZqr9vq9YunBgYxxu3Pnl5vy4BwBsqA+r/nV1Y+sH6rO1
            NzQs4PUF1YNnORLbbarF1k5mOihw38bevmTUGwDYON9avav1Q/W9tfdXr6y+u7porgPABznUNIut
            GfUG7ssvN/4cc2TxqgEAdum86inVm1o/XN9bO179y+qj5joAnNWRxr+G9twF7svY1c3vrv548aoB
            AHbhS6vXtH64/tD2tupHq0fP13V24fsb/5oeXbxqYFO8u/HnmB9avGoAgPvxmdUrWj9g37O9r/q5
            6our8+frOnv0xsa9vtcvXzKwIZ7R+GvI7VlPAgDYJz61emnrh+wz7a7qNxtGQy+esd+MN8WUc6Pe
            wH15a+PPMVcuXjUAwD0cqn6murP1w/bdDffjfW/1MXN2msld27jX3ag3cF+m+HHvLxevGgCgenj1
            460ftO+u3lM9t2GaO5tpii/GVh8G7suxnGMAgA3ykIYFsf6ydcP2rQ1bxfyj6sJZe8xSxu7t/bLl
            SwY2xKGG64bgDQDsaw9s2HprihVix7Tfqb6peti83WUFRzMiBczneTm/AAD71AXV11dvab2w/ebq
            +6pHzdxX1ncio97APHYaVigXvAGAfeUrqte3XuD+4+rS2XvJfjLFqPehxasGNsVetxc7tUaxAMDB
            9vnVq1ovcP9udXj2XrJfjR31PrZ8ycAGeXW7P69csUqlAMCB9BkN91CvFbhf1RD62W5XZNQbmM9O
            wxaE53o+OX76MQAAoxxp3Xu4X98wrR1q+IJ7qnHvqZcsXjWwSXY6t50UhG4AYBK/1HqB+y3VNzQs
            4Ab3NHbU+858WQbObqfhXHNvP/Sdqq7KeQQAGOkLqr9oncD9Fw1bk100ey/ZVDuNf5/9/OJVA5vq
            soYQfkXDLDCBGwAY5SOrF7ZO4L6p+jfVQ2bvJQfBsca9325ZvmQAAGDbfV3rjHLfVv1E9fD5u8gB
            cqjx7z2r4wMAAIv4hOq3W2eU+4VZYZq9Gzvq/dzlSwYAALbJA6tnVbe2fOB+afWp83eRA27sqPf1
            y5cMAABsiydUb2j5wP2K6nMW6B/b40Tj3pOmmwMAAJN6WPXvq7taNnC/pvrSBfrH9nlm496bVy1f
            MgAAcFA9ubqhZQP3m6qnVOct0D+2122Zbg4AAKzokurXWjZw31B9c/WABfoHYxdZM90cAADYkwuq
            76ze13KB+1T1r6sPW6B/cMZlmW4OAAAs7NOr17Zc4H5/9WPVhy/RObgXpzLdHAAAWMBDqh9v2Wnl
            11Ufs0Tn4CxMNwcAAGb3pdXbWi5w/0X1jxbpGdw/080BAIDZfHT14pYL3LdU35uF09h/TDcHAAAm
            dV71LdV7Wy50/2b1cUt0DvbAdHMAAGAyj6le1XKB+13VUxfpGezdkUw3BwAARnpQ9W+rO1oudD8/
            q5WzOU5kujkAALBHf79xoWK37Q3VpYv0DKZzVaabAwAAu3RJ9fqWC9y3Vd9fPXCJzsHEDme6OQAA
            sAvfVd3VcqH75dUnL9IzmM+YmSEnV6gXAABYwYOrX225wH2y+qcNK6XDphs73dwtFgAAcMB9TvXW
            lgvdP1s9YpGewTLGTje/dvmSAQCAJTyw+uHqzpYJ3CcaFmyDg2jMdPNbVqgXAACY2WOq17VM4L6j
            +pGGrcngoBo73fzQ8iUDAABzuKD6noaVxJcI3a9qCPlw0I2dbn758iUDAABT+7jqD1omcL+3+tYs
            nsZ2ubW9f2bc5w0AABvun1c3tUzo/q/Vxy7TLdhXjjfus7OzfMkAAMBYj6x+o2UC91urL1+mW7Av
            PaVxn6HLli8ZAAAY46ur9zR/4L6z+onqIct0C/a1MZ+lYyvUCwAA7MFO9aKWGeX+o+ozl+kWbISr
            2/vn6foV6gUAAHbpS6q3N3/gfn/D6ugXLNMt2BiXN+6zdXj5kgEAgHNxcfW8lhnlfnN1yTLdgo1z
            qHGfL9uKAQDAPvTZ1ZtaZpT7+xbqE2yyE+39c2ZbMQAA2EceUP1ww+Jmc4fua6qPWqZbsPGuatzn
            zbZiAACwDzymem3zB+4bq69bqE9wUFzWuM/d0eVLBgAAzrigYVGz25o/dP9W7uWGvRrz2bOtGAAA
            rOTjqj9o/sB9S/WM6rxlugUH0phtxU6uUC8AAGy9f17d1Pyh+9XVoxfqExxkRxv3WbStGAAALOSR
            1W80f+C+o2HF8guX6RYceGO3Fbti+ZIBAGD7fHXD4mZzh+43VI9bqE+wTY6398/l/1yhXgAA2BqP
            bJkVy+9u2PboomW6BVvHtmIAALAPfW51e/MH7rdWn7dQn2BbHWnc5/RZy5cMAAAH2ze3zCj3C6qH
            LtQn2Han2vtn9Y0r1AsAAAfShdXzmj9wv7t60kJ9AgZjthW7Y4V6AQDgwHl49crmD90vqT5qoT4B
            HzB2W7HLli8ZAAAOjsdXb2vewH1T9Q1LdQj4K8ZuK3Zs+ZIBAOBgeGp1W/OG7t+qLlmqQ8B9uqm9
            f45PrlAvAABsvH/XvIH7luoZ1XlLdQg4q5/OdHMAAFjEEvdzv7p69FIdAs6J6eYAALCAue/nvqP6
            voYV0oH9508y3RwAAGYz9/3cb6j+zmK9Afbi8kw3BwCAyS2xP/cLq4uW6hCwZ6abAwDAxB5R/W7z
            Be47G0bSgc1xPNPNAQBgEnPfz/1n1aMW6w0wFdPNAQBgAnPfz/0z1QMX6w0wJdPNAQBghLnv5769
            evpivQHmYro5AADswdz7c7+zesJivQHmZLo5AADs0tz3c/9h9dGL9QaYm+nmAACwC+7nBvZizHTz
            G1eoFwAAFud+bmAM080BAOAs3M8NjDV2uvkrli8ZAACW8cSGVYXnCt3u54btcaK9nyvuWqFel79h
            RwAADIlJREFUAACY3XObL3Dfnfu5Ydtc1bhzxncsXzIAAMzj4ur3mi9wu58bttPhxp07rlu+ZAAA
            mN751cubL3S7nxu226nGnUMOLV8yAABM5/zqp5svdLufG3hp484jVyxfMgAATGPu0O1+bqDqcxt3
            Lrl++ZIBAGC8OUO3+7mBD/Wexp1XjixfMgAA7N2codv93MC9eXrjzi3Hli8ZAAD2bq7Q7X5u4L7s
            NP4cs7N41QAAsAfPaZ7Q7X5u4P4ca9x55ujyJQMAwO7MEbrdzw2cqyONO9/Y0xsAgH1tjtD9ntzP
            DezOicaddw4vXzIAANy/K5s+dF+f+7mB3buiceeeq5YvGQAAzm7sl9x7a3+4aA+Ag+RQ484/J5cv
            GQAA7tscofuFi/YAOIiubdx56LLlSwYAgL9qjtD9nEV7ABxURxt3LnrF8iUDAMAHE7qB/e5U485J
            n7Z8yQAAMPjuhG5g/xu7p/c1y5cMAAB1edOH7p9etAfAtjjc+PPTocWrBgBgq80Vus9fshPAVjne
            uHPUseVLBgBgWwndwCaa4txl1BsAgNl9fUI3sJl2Gn++MuoNAMCsvr66M6Eb2Fxvyqg3AAD71Byh
            +5cSuoFlPbHx564XL141AAAH3lyh+8IlOwFw2onGn8OOLF41AAAH1rc0beAWuoG1TbG12MsWrxo2
            2071ZdWzT7cvO/3fAGDrfXtCN3AwHcuoN8xtp/rahtsz7utz9JzVqgOAfeAx1V0J3cDBdCij3jCX
            y6rnd+6fpesaZqIAwFZ5cPXupg3dv5rQDewvRr1hOp9X/Vp1c3v7LF2/fMkAsK4XNH3ovmjRHgDc
            P6PeMM5HVT9a3dg03xeuWLZ8AFjP0YRuYHtMMer99MWrhvV8RPVN1bVNf0vayQX7AQCreXz1voRu
            YHscqk417lx3W1Zn5mB7aMMP89dUdzRt2P7Q5vYNAA60nabZ21boBjbNFY0/57188aphXg+uvrr6
            Lw0/Ls0Ztu/Zji7ROQBYy0ua7qL58oRuYHPsNH7U++7cn8rB8BXVz7Vc0PY5AmBrfHfThu6Lly0f
            YLQpRr2N1rGJLqyeWP1M0y2SNqbZVgyAA+lIdWfTXCyvT+gGNtNUo94nExzY/86vvrD699VftH7Y
            PtNOzdlpAFjLI6obmuZieVf1vy1bPsCkphr1PtmwaBvsN59d/Xj19tYP2ffWrpqv6wCwjgc07D87
            xYXyzurvLls+wOR2qvc2zXnxuqx0zv7w+OpHqje3frA+Wzs+1wEAgDVd1XQXy8sXrh1gLlc23bnx
            xQvXDmd8QvVvqj9t/UB9Lu1EZokAcAD97013sXzhwrUDzO2mpjtHmjrLUh5Zvah6d+sH6XNtp7KK
            OQAH1KOr9zXNBfN1Dft8AhwkT2zacGGlc+by8dV3VX/c+iF6N+14w2w5t2MAcCA9uCEsT3HRPNkQ
            4gEOomc1bdB42rLlc4A9rvr+6rWtH6B30040hG1TygE48F7YdBfQJy1cO8DSntt058y7qicvWz4H
            xHnV51Q/Vv2v1g/Qu2m3Vdc0LPAGAFvh8qa7kF65cO0AaznWtEHEtHPOxYXVF1fPa7ptP5dqNzXs
            mvKUyY8KAOxzj69ub5oL6suq85ctH2A1Ow33o04ZTJ69aA/YFA+uvrJhdtqNrR+gd9NOVv+x+sJ8
            RwBgSz2i4b6qKS6sJ04/H8A22WlYfXnKoPL8RXvAfvXh1dc0bD13c+sH6N20m6tfrC6rHjj1gQGA
            TXJ+wwj1FBfY23OPFrC9Djd9+L4uqzpvo0dW/7z679UdrR+gd9PuqH614ceCi6c+MACwqa5ouovt
            5QvXDrDfHG36IHNdQ6jnYPv46jur321YaG/tAL3b9jvVN1cfOfWBAYBN96Tqzqa54L5w4doB9qsp
            f9A8004mfB80D6+eXr286WdKLNX+qPqe6pKJjw0AHBiXNHyRm+LC+7qGBV8AGEy90vmZ9vQlO8Gk
            Prlhr/Zj1RtbPzTvtf1Z9YPVY6c9PABw8Dyg+sOmuQCfrB69bPkAG+HPmyf4/N+573u/e0D1WdV3
            VFdX72r9wDymvbP6qYa9ws+b8DgBwIH2vKa7GD9p4doBNsVOdWvzBKGbG7YcE8D3h4dWX1r9QPXb
            rR+Up2h/2XAb2T9o2DMcANiFKRf+uXLh2gE2zaUNOz7MFY5OJoCv4VD1lOq5Dfc5b+JiaPfW7qj+
            S/VV1YMmO1oAsGUeXd3SNBfnlzVsRQbA2V1a3di8gUkAn8/51eOqb6l+oXpr6wfkqdtbq2dUD5vo
            mAHA1tqp/qJpLtAnqkcsWz7ARtup/qT5A9TJ6msX6tNB9tTqP1dvaL7bBdZs76le2rBY30UTHTMA
            oHpT01ysb68ev3DtAAfBTvOtdv6h7foE8LM5v3pU9cUNI9k/Uf1a9Y7WD8VztbdV/676otyzDQCz
            uLLpLtzftHDtAAfNVS0Xtm6prqm+rOG+5G3zyOrzGrbx+rcNK4y/roM5in1v7Y2n+/1ZWY0cZuGD
            BZyx07CNyRS/br+gYXE2AMY5Wj1/hX/3RHVtdbz6rdN/brqHNOyR/Ykf0j7h9P+2bV5Tvbj6lYaF
            34AZCd7AGVdW/2qC5/l/G0YNbp7guQCoIw0jsGsvaHVtHxzGT61bzgfZqR57+u+Hq4+uPrMhaN/V
            sI3XxeuUtm/cXb2yIWi/qOHHFWAhgjdQwxeWtzT+F/9TDV943jy6IgDu6XBD+L5k7ULu4UT12oZz
            /msapit3+u9ThvLPP/3noT4wDf7I6T8PZ4X2s7mjYXeRMyPb71y3HNheFkwAqi5vmml2X5PQDTCH
            4w0h8/cbpkfvB/cMwvfnVPc+Xf1Ew33UlzSsmr3TsKXabp6bD3ZzwwJwv9KwIvmN65YDAJxxqvEL
            s1y5eNUA22en6Xaf0A5OO1n9TPXl2fYLAPalyxp/wX95w5YrACzj6tYPe9q67Y7qvzdsdQYA7HNj
            94q9vXrE4lUD8MTqhtYPgNoy7ebqDxtGtp8WALBRrm3cF4FvXL5kAO7hSOPP5dr+a29o+HH8n1Wf
            lpllALDRrmjvXwquW6FeAO6dAL657aaG1cd/sPqH1UcEABwoY4L3kXt5PgDWJYDv//am6merb64e
            V11wr68kAHBgHGpvXxquXaNYAM7Z0YbtutYOmdvebq5+u/rhhgVNH362Fw0AOLiuandfIk417CcL
            wP53tHpL6wfQbWk3V/+t+tbqM87h9QEAtsROdbxz/1Jx2TplAjDCsxp2olg7mB6kdrJh2virq5+u
            PumcXw0AYCvtdP/7wp5qGDkBYHM9s2EE/K7WD66b0G6sXlX9XPXs6p9Un15dvNsDD2yv89YuANh3
            jlaXV4+9x3+7sWE6+lUN4RuAg+Hw6Xbk9J+PPfv//UB7XcMWXm+o/rR6ffXGhr3SAUYRvIGzOdwQ
            tE+sXQgAi9jpA0H8TBh/2KoVTesdDbdWnQnXf9wwVfz6NYsCDj7BGwCAs7nnqPjjq09dt5z79OY+
            8EPxmZ03zkwV//WGEW2AVQjeAADsxk71JdWTq79dPbRhdtStp9vU09VvbBil7vSfp063M//N9pbA
            vid4AwAwt0On24c6M5r+yOqi6n9U11Q39YGQDQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
            AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
            AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
            AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
            AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
            AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
            AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
            AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
            AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
            AAAAAAAAAAAAAAAAAAAAAAAAbKf/D04QGf+ZN8VNAAAAAElFTkSuQmCC
            ">               
                            </div>
                        </div>    
                        <div>
                            <b>주소 : <u>서울특별시 동작구 상도로 369 정보과학관 옥상</u></b>
                        </div>
                        <div>
                            <b>전화번호 : <u>010-1234-5678</u></b>
                        </div>
                    </div>
                
                    </br>
                    
                    <div>
                        <div class="signature-container">
                            <div>
                                <b>채무자 : <u>김한슬</u></b>
                            </div>
                            <div style="position: relative; display: inline-block;">
                                <span>(서명 또는 인)</span>
                            </div>
                        </div>
                        <div>
                            <b>주소 : <u></u></b>
                        </div>
                        <div>
                            <b>전화번호 : <u></u></b>
                        </div>
                    </div>

                    </body>
                    </html>
        """.trimIndent()
    }
}