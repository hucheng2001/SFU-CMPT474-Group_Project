//Express server
const express = require('express');

const searchEngine = express();
searchEngine.use(express.json());

const PORT = process.env.PORT || 4002;

searchEngine.listen(PORT, () => {
  console.log(`Listening on port ${PORT}`);
});

//API Endpoints
//format: /search-engine/request/{input}

searchEngine.get("/search-engine/request/:input", async (req, res) => {
    const SEARCH_INPUT = req.params.input;
    
    const dummyData = "filtered search algorithm response (soon to be!)";

    res.send({ result : dummyData });
    return;
})