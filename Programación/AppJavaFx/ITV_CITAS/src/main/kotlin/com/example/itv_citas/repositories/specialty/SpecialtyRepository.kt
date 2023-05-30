package com.example.itv_citas.repositories.specialty

import com.example.itv_citas.errors.SpecialtyError
import com.example.itv_citas.models.Specialty
import com.example.itv_citas.repositories.SimpleCrud

interface SpecialtyRepository: SimpleCrud<Specialty, String, SpecialtyError>