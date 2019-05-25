package com.janikibichi.six

case class ValueUpdate(newValue: Double)
case class StockValue(value: Double, timestamp: Long = System.currentTimeMillis())
case class ValueAppended(stockValue: StockValue)
case class StockHistory(values: Vector[StockValue] = Vector.empty[StockValue]){
    def update(event: ValueAppended) = copy(values :+ event.stockValue)
    override def toString = s"$values"
}