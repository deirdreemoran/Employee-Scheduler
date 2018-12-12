from django.shortcuts import render
from django import forms
from .forms import ScheduleForm
from .models import Schedule
import subprocess
from django.contrib.auth.models import User
import csv
import sqlite3
from sqlite3 import Error

hourOfDay = ["eightAM", "nineAM", "tenAM", "elevenAM", "twelvePM", "onePM", "twoPM", "threePM", "fourPM", "fivePM", "sixPM", "sevenPM", "eightPM", "ninePM", "tenPM", "elevenPM"]

def home(request):
	return render(request, 'myprojapp/home.html')

def create_connection(db_file):
	try:
		conn = sqlite3.connect(db_file)
		return conn
	except Error as e:
		print(e)
	return None

def getSchedule1(conn):
	cur = conn.cursor()
	cur.execute("SELECT studentSchedule1 FROM myprojapp_studentSchedule")
	rows = cur.fetchone()
	sizeRow = len(rows[0]) - 1
	myRow = rows[0]
	correctedRows = myRow[1:sizeRow]
	return correctedRows

def getSchedule2(conn):
	cur = conn.cursor()
	cur.execute("SELECT studentSchedule2 FROM myprojapp_studentSchedule")
	rows = cur.fetchone()
	sizeRow = len(rows[0]) - 1
	myRow = rows[0]
	correctedRows = myRow[1:sizeRow]
	return correctedRows
 
def viewSchedule(request):
	id_of_this = request.user.id
	myStr = "";
	for key, value in request.POST.items():
		if value == "0" or value == "1":
			myStr = myStr + value
	try:
		thing_to_change = Schedule.objects.get(id=id_of_this)
		if myStr != "":
			thing_to_change.schedule = myStr
			thing_to_change.name = request.user.username
			thing_to_change.save(force_update=True)
	except Schedule.DoesNotExist:
		myInstance = Schedule.objects.create(id=request.user.id, name=request.user.username, schedule=myStr)
	
	try:
		existingScheduleId = Schedule.objects.get(id=id_of_this)
		existingSchedule = existingScheduleId.schedule
		arrayES = list(existingSchedule)
		try:
			Sunday = { hourOfDay[i] : arrayES[i] for i in range(0, 16) }
			Monday = { hourOfDay[i] : arrayES[i+16] for i in range(0, 16) }
			Tuesday = { hourOfDay[i] : arrayES[i+32] for i in range(0, 16) }
			Wednesday = { hourOfDay[i] : arrayES[i+48] for i in range(0, 16) }
			Thursday = { hourOfDay[i] : arrayES[i+64] for i in range(0, 16) }
			Friday = { hourOfDay[i] : arrayES[i+80] for i in range(0, 16) }
			Saturday = { hourOfDay[i] : arrayES[i+96] for i in range(0, 16) }
			
			things = { 
				'Sunday': Sunday,
				'Monday': Monday,
				'Tuesday': Tuesday,
				'Wednesday': Wednesday,
				'Thursday': Thursday,
				'Friday': Friday,
				'Saturday': Saturday					
			}
			context = {
				'things': things
			}
			return render(request, 'myprojapp/viewSchedule.html', context);
		except:
			Sunday = { hourOfDay[i] : "0" for i in range(0, 16) }
			Monday = { hourOfDay[i] : "0" for i in range(0, 16) }
			Tuesday = { hourOfDay[i] : "0" for i in range(0, 16) }
			Wednesday = { hourOfDay[i] : "0" for i in range(0, 16) }
			Thursday = { hourOfDay[i] : "0" for i in range(0, 16) }
			Friday = { hourOfDay[i] : "0" for i in range(0, 16) }
			Saturday = { hourOfDay[i] : "0" for i in range(0, 16) }
			
			things = { 
				'Sunday': Sunday,
				'Monday': Monday,
				'Tuesday': Tuesday,
				'Wednesday': Wednesday,
				'Thursday': Thursday,
				'Friday': Friday,
				'Saturday': Saturday					
			}
			context = {
				'things': things
			}
			return render(request, 'myprojapp/viewSchedule.html', context);
	except Schedule.DoesNotExist:
		Sunday = { hourOfDay[i] : "0" for i in range(0, 16) }
		Monday = { hourOfDay[i] : "0" for i in range(0, 16) }
		Tuesday = { hourOfDay[i] : "0" for i in range(0, 16) }
		Wednesday = { hourOfDay[i] : "0" for i in range(0, 16) }
		Thursday = { hourOfDay[i] : "0" for i in range(0, 16) }
		Friday = { hourOfDay[i] : "0" for i in range(0, 16) }
		Saturday = { hourOfDay[i] : "0" for i in range(0, 16) }
		things = { 
			'Sunday': Sunday,
			'Monday': Monday,
			'Tuesday': Tuesday,
			'Wednesday': Wednesday,
			'Thursday': Thursday,
			'Friday': Friday,
			'Saturday': Saturday					
		}
		context = {
			'things': things
		}
		return render(request, 'myprojapp/viewSchedule.html', context);
		
 
	
def scheduler(request):
	subprocess.Popen("javac *.java", shell=True).communicate()
	subprocess.Popen("java -cp sqlite-jdbc-3.16.1.jar;. Scheduler", shell=True).communicate()
	database = "db.sqlite3"
	# create a database connection
	conn = create_connection(database)
	with conn:
		rows = getSchedule1(conn)
	arrayES = rows.split(",")
	Sunday = { hourOfDay[i] : arrayES[i] for i in range(0, 16) }
	Monday = { hourOfDay[i] : arrayES[i+16] for i in range(0, 16) }
	Tuesday = { hourOfDay[i] : arrayES[i+32] for i in range(0, 16) }
	Wednesday = { hourOfDay[i] : arrayES[i+48] for i in range(0, 16) }
	Thursday = { hourOfDay[i] : arrayES[i+64] for i in range(0, 16) }
	Friday = { hourOfDay[i] : arrayES[i+80] for i in range(0, 16) }
	Saturday = { hourOfDay[i] : arrayES[i+96] for i in range(0, 16) }

	things = { 
		'Sunday': Sunday,
		'Monday': Monday,
		'Tuesday': Tuesday,
		'Wednesday': Wednesday,
		'Thursday': Thursday,
		'Friday': Friday,
		'Saturday': Saturday					
	}
	context = {
		'things': things
	}
	return render(request, 'myprojapp/scheduler.html', context);

def scheduler2(request):
	subprocess.Popen("javac *.java", shell=True).communicate()
	subprocess.Popen("java -cp sqlite-jdbc-3.16.1.jar;. Scheduler", shell=True).communicate()
	database = "db.sqlite3"
	# create a database connection
	conn2 = create_connection(database)
	with conn2:
		rows2 = getSchedule2(conn2)
	arrayES2 = rows2.split(",")
	
	Sunday = { hourOfDay[i] : arrayES2[i] for i in range(0, 16) }
	Monday = { hourOfDay[i] : arrayES2[i+16] for i in range(0, 16) }
	Tuesday = { hourOfDay[i] : arrayES2[i+32] for i in range(0, 16) }
	Wednesday = { hourOfDay[i] : arrayES2[i+48] for i in range(0, 16) }
	Thursday = { hourOfDay[i] : arrayES2[i+64] for i in range(0, 16) }
	Friday = { hourOfDay[i] : arrayES2[i+80] for i in range(0, 16) }
	Saturday = { hourOfDay[i] : arrayES2[i+96] for i in range(0, 16) }
	
	things = { 
		'Sunday': Sunday,
		'Monday': Monday,
		'Tuesday': Tuesday,
		'Wednesday': Wednesday,
		'Thursday': Thursday,
		'Friday': Friday,
		'Saturday': Saturday					
	}

	context = {
		'things': things
	}
	
	return render(request, 'myprojapp/scheduler2.html', context);
