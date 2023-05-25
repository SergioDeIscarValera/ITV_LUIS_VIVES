package repositories.vehicle

import errors.VehicleError
import models.Vehicle
import repositories.SimpleCrud

interface VehicleRepository: SimpleCrud<Vehicle, String, VehicleError>