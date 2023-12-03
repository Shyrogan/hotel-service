#!/bin/bash

database_url=$1
database_user=$2
database_password=$3

start_hotel() {
  hotel_name=$1
  etoiles=$2
  adresse_numero=$3
  adresse_rue=$4
  adresse_ville=$5
  adresse_pays=$6
  initialChambres=$7
  server_port=$8
  database_url=$9
  database_user=${10}
  database_password=${11}

  java -Dspring.datasource.url="${database_url}" \
       -Dspring.datasource.username="${database_user}" \
       -Dspring.datasource.password="${database_password}" \
       -Dserver.address=127.0.0.1 \
       -Dserver.port=${server_port} \
       -Dhotel.name="${hotel_name}" \
       -Dhotel.etoiles=${etoiles} \
       -Dhotel.adresse.numero=${adresse_numero} \
       -Dhotel.adresse.rue=${adresse_rue} \
       -Dhotel.adresse.ville=${adresse_ville} \
       -Dhotel.adresse.pays=${adresse_pays} \
       -Dhotel.initialChambres=${initialChambres} \
       -jar hotel-service-0.0.1-SNAPSHOT.jar
}

start_hotel "IbisBudget" 4 6 "Barthez" "Montpellier" "France" 4 3000 "${database_url}" "${database_user}" "${database_password}" &
sleep 5
start_hotel "HotelLux" 5 10 "LuxStreet" "Paris" "France" 6 3001 $1 $2 $3 &
sleep 5
start_hotel "EcoStay" 3 2 "EcoRue" "Lyon" "France" 8 3002 $1 $2 $3 &
