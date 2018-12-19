from django.contrib import admin
from . import views
from django.urls import path

urlpatterns = [
	path('', views.home, name='myprojapp-home'),
	path('scheduler/', views.scheduler, name='myprojapp-scheduler'),
	path('viewSchedule/', views.viewSchedule, name='myprojapp-viewSchedule')
]