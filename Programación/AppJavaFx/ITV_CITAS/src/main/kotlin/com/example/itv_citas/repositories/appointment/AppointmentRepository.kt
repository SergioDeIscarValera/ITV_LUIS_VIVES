package com.example.itv_citas.repositories.appointment

import com.example.itv_citas.errors.AppointmentError
import com.example.itv_citas.models.Appointment
import com.example.itv_citas.repositories.CrudRepository
import java.time.LocalDateTime

interface AppointmentRepository:CrudRepository<Appointment, Long, AppointmentError> {
    fun findByIdEmployee(idEmployee: Long): List<Appointment>
    fun findByIdEmployee(idEmployee: Long, startDate: LocalDateTime, endDate: LocalDateTime): List<Appointment>
    fun findByCarNumber(carNumber: String): List<Appointment>
    fun findByDate(carNumber: String, startDate: LocalDateTime, endDate: LocalDateTime): List<Appointment>
}