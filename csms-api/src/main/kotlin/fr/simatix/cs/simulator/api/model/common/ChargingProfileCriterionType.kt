package fr.simatix.cs.simulator.api.model.common

import fr.simatix.cs.simulator.api.model.common.enumeration.ChargingLimitSourceEnumType
import fr.simatix.cs.simulator.api.model.common.enumeration.ChargingProfilePurposeEnumType

data class ChargingProfileCriterionType (
    val chargingProfilePurpose : ChargingProfilePurposeEnumType?=null,
    val stackLevel : Int?=null,
    val chargingProfileId : List<Int>?=null,
    val chargingLimitSource : List<ChargingLimitSourceEnumType>?=null
)