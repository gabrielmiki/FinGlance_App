package miki.learn.finglance.data.mapper

import miki.learn.finglance.data.local.PortfolioEntity
import miki.learn.finglance.domain.model.PortfolioStock

fun PortfolioEntity.toPortfolioStock(): PortfolioStock {
    return PortfolioStock(
        name = name,
        symbol = symbol,
        amount = amount,
        price = price,
        // id = id
    )
}

fun PortfolioStock.toPortfolioEntity(): PortfolioEntity {
    return PortfolioEntity(
        name = name,
        symbol = symbol,
        amount = amount,
        price = price,
        // id = id
    )
}