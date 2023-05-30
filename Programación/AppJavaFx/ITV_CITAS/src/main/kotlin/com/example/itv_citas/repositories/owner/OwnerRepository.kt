package com.example.itv_citas.repositories.owner

import com.example.itv_citas.errors.OwnerError
import com.example.itv_citas.models.Owner
import com.example.itv_citas.repositories.SimpleCrud

interface OwnerRepository: SimpleCrud<Owner, String, OwnerError>