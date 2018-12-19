from django.shortcuts import render
from django import forms
from .forms import ScheduleForm
from .models import Schedule
import subprocess
from django.contrib.auth.models import User
import csv
import sqlite3
from sqlite3 import Error
from .utils import create_connection, getSchedule1, getSchedule2, getNames
from .utils import runSchedulerJavaScript, fillExistingSchedule, fillEmptySchedule

database = "db.sqlite3"

def home(request):
	return render(request, 'myprojapp/home.html')

def viewSchedule(request):
	id_of_this = request.user.id
	thing_to_change = Schedule.objects.get(id=id_of_this)
	myStr = thing_to_change.schedule;
	myStr2 = ""
	for key, value in request.POST.items():
		if value == "0" or value == "1":
			myStr2 = myStr2 + value
	#if there's no post request, get the users existing data and display
	if myStr2 == "":
		existingScheduleId = Schedule.objects.get(id=id_of_this)
		existingSchedule = existingScheduleId.schedule
		arrayES = list(existingSchedule)
		things = fillExistingSchedule(arrayES)
		context = {
			'things': things
		}
		return render(request, 'myprojapp/viewSchedule.html', context);
	#get post request and update account
	else:
		thing_to_change.schedule = myStr2
		thing_to_change.name = request.user.username
		thing_to_change.save(force_update=True)
		existingScheduleId = Schedule.objects.get(id=id_of_this)
		existingSchedule = thing_to_change.schedule
		arrayES = list(existingSchedule)
		things = fillExistingSchedule(arrayES)
		context = {
			'things': things
		}
		return render(request, 'myprojapp/viewSchedule.html', context);

def scheduler(request):
	runSchedulerJavaScript()
	# create a database connection
	conn = create_connection(database)
	with conn:
		rows = getSchedule1(conn)
		rows2 = getSchedule2(conn)
		names = getNames(conn)
	arrayES = rows[0].split(",")
	arrayES2 = rows2[0].split(",")
	numShifts = rows[1].split(",")
	total = 0
	for shift in numShifts:
		if shift != "":
			numm = int(shift)
			total = total + numm
	sched = fillExistingSchedule(arrayES)
	sched2 = fillExistingSchedule(arrayES2)
	nameNumShifts = dict(zip(names, numShifts))
	context = {
		'things': sched,
		'things2': sched2,
		'nameNumShifts': nameNumShifts,
		'total': total
	}
	return render(request, 'myprojapp/scheduler.html', context)



