package com.example.fitplan.room

import com.example.fitplan.model.Plan
import com.example.test.util.EntityMapper
import javax.inject.Inject

class PlanCacheMapper @Inject constructor() : EntityMapper<PlanCacheEntity, Plan> {

    override fun mapFromEntity(entity: PlanCacheEntity): Plan {
        return Plan(
            id = entity.id,
            name = entity.name,
            imageUrl = entity.imageUrl,
            imageSmallUrl = entity.imageSmallUrl,
            athleteId = entity.athleteId,
            athleteFirstName = entity.athleteFirstName,
            athleteLastName = entity.athleteLastName,
            athleteSlug = entity.athleteSlug,
            slug = entity.slug,
            singleLength = entity.singleLength,
            daysCount = entity.daysCount,
            sex = entity.sex,
            daysPerWeek = entity.daysPerWeek,
            location = entity.location,
            type = entity.type,
            presentationType = entity.presentationType,
            metadata = entity.metadata,
            displayPriority = entity.displayPriority,
            free = entity.free,
            modifiedTimestamp = entity.modifiedTimestamp,
            available = entity.available,
            allowFreeAccess = entity.allowFreeAccess,
            imageTvUrl = entity.imageTvUrl,
            description = entity.description
        )
    }

    override fun mapToEntity(domainModel: Plan): PlanCacheEntity {
        return PlanCacheEntity(
            id = domainModel.id,
            name = domainModel.name,
            imageUrl = domainModel.imageUrl,
            imageSmallUrl = domainModel.imageSmallUrl,
            athleteId = domainModel.athleteId,
            athleteFirstName = domainModel.athleteFirstName,
            athleteLastName = domainModel.athleteLastName,
            athleteSlug = domainModel.athleteSlug,
            slug = domainModel.slug,
            singleLength = domainModel.singleLength,
            daysCount = domainModel.daysCount,
            sex = domainModel.sex,
            daysPerWeek = domainModel.daysPerWeek,
            location = domainModel.location,
            type = domainModel.type,
            presentationType = domainModel.presentationType,
            metadata = domainModel.metadata,
            displayPriority = domainModel.displayPriority,
            free = domainModel.free,
            modifiedTimestamp = domainModel.modifiedTimestamp,
            available = domainModel.available,
            allowFreeAccess = domainModel.allowFreeAccess,
            imageTvUrl = domainModel.imageTvUrl,
            description = domainModel.description
        )
    }

    fun mapFromEntityList(entities: List<PlanCacheEntity>): List<Plan> {
        return entities.map { mapFromEntity(it) }
    }
}