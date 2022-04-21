package fr.simatix.cs.simulator.core20.model.common

import fr.simatix.cs.simulator.core20.model.common.enumeration.ChargingLimitSourceEnumType
import fr.simatix.cs.simulator.core20.model.common.enumeration.ChargingProfilePurposeEnumType

data class ChargingProfileCriterionType (
    val chargingProfilePurpose : ChargingProfilePurposeEnumType?=null,
    val stackLevel : Int?=null,
    val chargingProfileId : List<Int>?=null,
    val chargingLimitSource : List<ChargingLimitSourceEnumType>?=null
)