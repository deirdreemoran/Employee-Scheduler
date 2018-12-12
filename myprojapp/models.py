from django.db import models
from django.contrib.auth.models import User

class Schedule(models.Model):
    name = models.CharField(max_length=500)
    schedule = models.CharField(max_length=1000)

