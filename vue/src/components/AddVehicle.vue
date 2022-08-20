<template>
    <form id="vehForm" v-on:submit.prevent="addVehicle">
      <div class="vehicleForm">
        <label for="year">Year: </label>
        <select v-model="vehicle.year">
          <option
            v-for="year in years"
            v-bind:key="year.id"
            :value="year"
          >
            {{ year }}
          </option>
        </select>
      </div>
      <div class="vehicleForm">
        <label for="make">Make: </label>

        <select v-model="vehicle.make" v-show="this.vehicle.year != ''">
          <option
            v-for="make in makes.slice().sort()"
            v-bind:key="make.id"
            :value="make"
          >
            {{ make }}
          </option>
        </select>
      </div>
      <div class="vehicleForm">
        <label for="model">Model: </label>
        <select v-model="vehicle.model" v-show="this.models != ''">
          <option
            v-for="car in models.slice().sort((a, b) => (a.Model_Name > b.Model_Name) ? 1 : ((b.Model_Name > a.Model_Name) ? -1 : 0))"
            v-bind:key="car.id"
            :value="car.Model_Name"
          >
            {{ car.Model_Name }}
          </option>
        </select>
      </div>
      <div class="vehicleForm">
        <label for="color" >Color: </label>
        <select v-model="vehicle.color" v-show="this.vehicle.model != ''">
          <option
            v-for="color in colors"
            v-bind:key="color.id"
            :value="color"
          >
            {{ color }}
          </option>
        </select>
      </div>

        <button class="buttonstyle" type="submit" value="Save">Save</button>
      </form>
</template>

<script>
import APIService from "@/services/APIService";
import VehicleService from "@/services/VehicleService";

export default {
  name: "AddVehicle",
  data() {
    return {
      vehicle: {
        make: "",
        model: "",
        year: "",
        color: "",
      },
      makes: [
        "Buick",
        "MINI",
        "Volvo",
        "Ford",
        "HUMMER",
        "GMC",
        "Subaru",
        "Mitsubishi",
        "Dodge",
        "Nissan",
        "Honda",
        "Lincoln",
        "Hyundai",
        "BMW",
        "Bentley",
        "Lexus",
        "Chevrolet",
        "Jaguar",
        "Mercedes-Benz",
        "Volkswagen",
        "Aston Martin",
        "Land Rover",
        "Pontiac",
        "Cadillac",
        "FIAT",
        "Saab",
        "Kia",
        "Lamborghini",
        "Audi",
        "Jeep",
        "MAZDA",
        "Suzuki",
        "Toyota",
        "Acura",
        "Saturn",
        "Chrysler",
        "Isuzu",
        "Ferrari",
        "Tesla",
        "INFINITI",
        "Oldsmobile",
        "Ram",
        "Eagle",
        "Porsche",
        "Mercury",
        "Scion",
        "Lotus",
        "Plymouth",
        "Freightliner",
        "Rolls-Royce",
        "SRT",
        "Maybach",
        "Alfa Romeo",
        "Geo",
        "smart",
        "Daewoo",
        "Maserati",
        "Daihatsu",
        "Genesis",
        "McLaren",
        "Fisker",
        "Panoz"
      ],
      models: [],
      years: [],
      colors: [
        "Black",
        "Blue",
        "Brown",
        "Gold",
        "Gray",
        "Green",
        "Orange",
        "Purple",
        "Red",
        "Silver",
        "Tan",
        "White",
        "Yellow",
      ]
    };
  },
  methods: {
    addVehicle() {
      VehicleService.addVehicle(this.vehicle).then((response) => {
        if (response.status === 201 || response.status === 200) {
          const vehicles = [...this.$store.state.vehicles]
          vehicles.push(response.data);
          this.$store.commit("SET_VEHICLES", vehicles);

          this.vehicle = {};

          this.$router.push("/requests");
        }
      });
    },
  },
  watch: {
    "vehicle.make": function (){
        APIService.getVehicleModels(this.vehicle.year, this.vehicle.make).then((response) => {
            this.models = response.data.Results;
        })
    }
  },
  created() {
    let currentYear = '2022'; 
    let years = [];
    let startYear = '1996';  
    while ( startYear <= currentYear ) {
        years.push(startYear++);
    }   
    this.years = years;

  },
};
</script>

<style>
.vehicleForm {
  text-align: center;
  font-family: "Assistant", Arial, Helvetica, sans-serif;
  margin-right: 24px;
  margin-bottom: 20px;
  margin-top: 20px;
  font-size: 17px;
  color: #00008b;
  font-weight: bold;
}

#vehForm {
  margin: 0 auto;
}
</style>