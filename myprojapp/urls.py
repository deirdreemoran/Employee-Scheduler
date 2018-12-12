from django.contrib import admin
from . import views
from django.urls import path

urlpatterns = [
	path('', views.home, name='myprojapp-home'),
	path('scheduler/', views.scheduler, name='myprojapp-scheduler'),
	path('scheduler2/', views.scheduler2, name='myprojapp-scheduler2'),
	path('viewSchedule/', views.viewSchedule, name='myprojapp-viewSchedule')
]