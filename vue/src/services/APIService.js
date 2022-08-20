import axios from "axios";


export default {



    getVehicleModels(year, make) {
        return axios.get(`/vehicle/models/${year}/${make}`);
    }


}