package com.example.pw2.lesson8.data

class QualityRepository(private val dao: QualityReadingDao) {
    suspend fun insert(reading: QualityReadingEntity) = dao.insert(reading)
    suspend fun getAll() = dao.getAll()
}
