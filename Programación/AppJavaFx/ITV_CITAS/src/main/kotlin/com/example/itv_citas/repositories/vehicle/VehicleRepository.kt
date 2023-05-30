package com.example.itv_citas.repositories.vehicle

import com.example.itv_citas.errors.VehicleError
import com.example.itv_citas.models.Vehicle
import com.example.itv_citas.repositories.SimpleCrud

interface VehicleRepository: SimpleCrud<Vehicle, String, VehicleError>