package repositories.owner

import errors.OwnerError
import models.Owner
import repositories.SimpleCrud

interface OwnerRepository: SimpleCrud<Owner, String, OwnerError>