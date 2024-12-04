package com.inner.second.feature.home.contract

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.inner.second.core.designsystem.R
import com.inner.second.core.designsystem.theme.Purple
import com.inner.second.core.model.Contract

@Composable
fun SecondContractList(
    modifier: Modifier = Modifier,
    contractList: List<Contract> = emptyList(),
) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(contractList.size) { index ->
            key(contractList[index].id) {
                SecondContractListItem(contract = contractList[index])
            }
        }
    }
}

@Composable
fun SecondContractListItem(
    contract: Contract,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_note_outlined),
                contentDescription = "startNoteIcon"
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = contract.title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = contract.createdAt,
                    fontSize = 12.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }

            contract.dueDate?.let { dueDate ->
                Spacer(modifier = Modifier.width(16.dp))
                OutlinedCard(
                    shape = RoundedCornerShape(4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                    ),
                    border = BorderStroke(
                        width = 1.dp,
                        color = Purple
                    )
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                        text = dueDate,
                        fontSize = 12.sp,
                        color = Purple
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun SecondContractListPreview() {
    SecondContractList(
        contractList = listOf(
            Contract(
                id = 1,
                title = "금전소비대차계약서",
                createdAt = "10.9 월 오후 1:12",
                dueDate = "D-5"
            ),
            Contract(
                id = 1,
                title = "금전소비대차계약서",
                createdAt = "10.9 월 오후 1:12",
                dueDate = null
            ),
            Contract(
                id = 1,
                title = "금전소비대차계약서",
                createdAt = "10.9 월 오후 1:12",
                dueDate = "D-5"
            )
        )
    )
}

@Composable
@Preview
fun SecondContractListItemPreview() {
    SecondContractListItem(
        contract = Contract(
            id = 1,
            title = "금전소비대차계약서",
            createdAt = "10.9 월 오후 1:12",
            dueDate = "D-5"
        )
    )
}