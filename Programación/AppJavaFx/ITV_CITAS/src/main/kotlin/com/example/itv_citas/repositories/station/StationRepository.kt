package com.example.itv_citas.repositories.station

import com.example.itv_citas.errors.StationError
import com.example.itv_citas.models.Station
import com.example.itv_citas.repositories.SimpleCrud

interface StationRepository: SimpleCrud<Station, Long, StationError>