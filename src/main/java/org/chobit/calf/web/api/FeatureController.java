package org.chobit.calf.web.api;

import org.chobit.calf.model.WorkModel;
import org.chobit.calf.service.FeatureService;
import org.chobit.calf.service.entity.Feature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author robin
 */
@RestController
@RequestMapping("/api/feature")
public class FeatureController {

    @Autowired
    private FeatureService featureService;

    @RequestMapping({"/data", ""})
    public List<Feature> data() {
        return featureService.findFeatures();
    }


    @GetMapping("/records/data/{featureId}")
    public List<WorkModel> records(@PathVariable("featureId") int featureId) {
        return featureService.findFeatureRecords(featureId);
    }

    @PostMapping("/records/add/{featureId}/{workId}")
    public int addRecord(@PathVariable("featureId") int featureId, @PathVariable("workId") int workId) {
        return featureService.addFeatureRecord(featureId, workId);
    }

    @PostMapping("/records/delete")
    public boolean deleteRecord(@RequestBody List<Integer> recordsIds) {
        return featureService.deleteFeatureRecords(recordsIds);
    }

}
