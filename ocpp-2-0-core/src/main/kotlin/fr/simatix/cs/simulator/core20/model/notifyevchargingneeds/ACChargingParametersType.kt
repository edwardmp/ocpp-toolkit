package fr.simatix.cs.simulator.core20.model.notifyevchargingneeds

data class ACChargingParametersType(
    val energyAmount: Int,
    val evMinCurrent: Int,
    val evMaxCurrent: Int,
    val evMaxVoltage: Int
)