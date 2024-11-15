package com.inner.second.feature.contract.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class ContractWebViewParameterProvider : PreviewParameterProvider<String> {
    override val values = sequenceOf(
        """
            <!DOCTYPE html>
            <html>
            <head>
            <meta charset="UTF-8">
            <title>계약서</title>
            <style>
            body {
                font-family: '맑은 고딕', sans-serif;
                line-height: 1.6;
                margin: 20px;
                font-size: 14px; 
            }

            h1, h2 {
                text-align: center;
                font-size: 1.5em; 
            }

            .contract-section {
                margin-bottom: 30px;
            }

            .contract-section h3 {
                margin-bottom: 10px;
                font-size: 1.2em; 
            }

            .signature {
                margin-top: 50px;
                text-align: right;
            }

            .signature-line {
                border-bottom: 1px solid #000; 
                width: 200px; 
                margin: 10px 0;
            }
            </style>
            </head>
            <body>

            <h1>계약서</h1>

            <div class="contract-section">
                <h2>제1조 (목적)</h2>
                <p>본 계약은 갑과 을 사이의 [계약 내용 간략하게 기술]에 관한 권리와 의무를 규정함을 목적으로 한다.</p>
            </div>

            <div class="contract-section">
                <h2>제2조 (용어의 정의)</h2>
                <p>본 계약에서 사용되는 용어의 정의는 다음과 같다.</p>
                <ul>
                    <li>“갑”이란 [갑의 정보 간략하게 기술]을 의미한다.</li>
                    <li>“을”이란 [을의 정보 간략하게 기술]을 의미한다.</li>
                </ul>
            </div>

            <div class="signature">
                <p>(갑) </p>
                <div class="signature-line"></div> <p>(인)</p>

                <p>(을) </p>
                <div class="signature-line"></div> <p>(인)</p>
            </div>

            </body>
            </html>
        """.trimIndent()
    )
}