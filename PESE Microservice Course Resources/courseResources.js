//Express server
const express = require('express');

const courseResource = express();
courseResource.use(express.json());

const PORT = process.env.PORT || 4000;

courseResource.listen(PORT, () => {
  console.log(`Listening on port ${PORT}`);
});


//API Endpoints
//format: /course-resources/{filter}/{resource}

courseResource.get("/course-resources/links/:course", async (req, res) => {
    const COURSE = req.params.course;

    //request to course-resource microservice
    const {Datastore} = require('@google-cloud/datastore');
    const datastore = new Datastore();

    const query = datastore
      .createQuery(COURSE);
    
    try {
      const [queries] = await datastore.runQuery(query);
      
      //response (success/fail)
      const dummyData = queries;
      res.send({ websites : dummyData});
      return;
    } catch (error){
      console.log("GET::Course Resource error: ", error);

      res.sendStatus(500);
    }
})