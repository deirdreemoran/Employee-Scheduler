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

def create_connection(db_file):
	try:
		conn = sqlite3.connect(db_file)
		return conn
	except Error as e:
		print(e)
	return None

def getSchedule1(conn):
	cur = conn.cursor()
	cur.execute("SELECT schedule1, scheduledShifts FROM myprojapp_scheduler WHERE id = 1")
	rows = cur.fetchone()
	return rows

def getSchedule2(conn):
	cur = conn.cursor()
	cur.execute("SELECT schedule2, scheduledShifts FROM myprojapp_scheduler WHERE id = 1")
	rows = cur.fetchone()
	return rows

def getNames(conn):
	cur = conn.cursor()
	cur.execute("SELECT name FROM myprojapp_schedule")
	rows = cur.fetchall()
	malist = []
	for name in rows:
		stnr = ','.join(name)
		malist.append(stnr)
	return malist

def fillExistingSchedule(arrayES):
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
	
	return things

def fillEmptySchedule():
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
	
	return things

def runSchedulerJavaScript():
	subprocess.Popen("javac *.java", shell=True).communicate()
	subprocess.Popen("java -cp sqlite-jdbc-3.16.1.jar;. Scheduler", shell=True).communicate()
