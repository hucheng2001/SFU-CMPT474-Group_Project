//Express server
const express = require('express');

const userPreference = express();
userPreference.use(express.json());

const PORT = process.env.PORT || 4001;

userPreference.listen(PORT, () => {
  console.log(`Listening on port ${PORT}`);
});


//API Endpoints
//format: /user-preferences/favourites

userPreference.get("/user-preference/favourites/:userId", async (req, res) => {    
    const userId = req.params.userId;

    //request to course-resource microservice
    const {Datastore} = require('@google-cloud/datastore');
    const datastore = new Datastore();

    const query = datastore
      .createQuery('profile');

    try {
      const [queries] = await datastore.runQuery(query);

      res.send({ user : queries});
      return;
    } catch (error){
      console.log("GET::User Preference error: ", error);

      res.sendStatus(500);
    }
})