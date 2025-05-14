import firebase_admin
from firebase_admin import credentials, db
import time

cred = credentials.Certificate("firebase-adminsdk.json")
firebase_admin.initialize_app(cred, {
    'databaseURL': 'https://<your-database>.firebaseio.com/'
})

ref = db.reference('busLocation')

while True:
    # Dummy coordinates for simulation
    ref.set({
        'latitude': 20.29,
        'longitude': 85.82
    })
    time.sleep(5)
