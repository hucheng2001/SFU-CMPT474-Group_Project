const express = require('express');

const gateway = express();
gateway.use(express.json());

const PORT = process.env.PORT || 8080;

gateway.listen(PORT, () => {
  console.log(`Listening on port ${PORT}`);
});


//API Endpoints
//format: /api/{microservice name}/{resource}

gateway.get("/api/course-resources/:course", (req, res) => {
    const COURSE = req.params.course;

    //request to course-resource microservice

    //response (success/fail)
    const dummyData = "RESPONSE!";
    res.send({ data : dummyData});
})