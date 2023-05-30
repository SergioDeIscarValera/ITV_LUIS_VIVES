package repositories.station

import errors.StationError
import models.Station
import repositories.SimpleCrud

interface StationRepository: SimpleCrud<Station, Long, StationError>