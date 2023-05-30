package repositories.specialty

import errors.SpecialtyError
import models.Specialty
import repositories.SimpleCrud


interface SpecialtyRepository: SimpleCrud<Specialty, String, SpecialtyError>