package nhnis.ontology.query;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import nhnis.ontology.seed.Mgcoa8888OntologySeed;
import nhnis.ontology.store.OntologyStore;

class ReverseImpactUnitTest {

    private OntologyQueryService query;

    @BeforeEach
    void setUp() {
        OntologyStore store = new OntologyStore();
        Mgcoa8888OntologySeed.seed(store);
        query = new OntologyQueryService(store);
    }

    @Test
    void impact_finds_serviceIds() {
        Map<String, Object> impact = query.impactByTable("TB_FW_IMAGE_LOG");
        @SuppressWarnings("unchecked")
        List<String> serviceIds = (List<String>) impact.get("affectedServiceIds");
        assertThat(serviceIds).as("impact=%s", impact).contains("mgcoa8888S0", "mgcoa8888D0");
        @SuppressWarnings("unchecked")
        List<String> handlers = (List<String>) impact.get("affectedHandlers");
        @SuppressWarnings("unchecked")
        List<String> programs = (List<String>) impact.get("affectedPrograms");
        @SuppressWarnings("unchecked")
        List<String> businesses = (List<String>) impact.get("affectedBusinesses");
        assertThat(handlers).contains("mgcoa8888Handler");
        assertThat(programs).contains("mgcoa8888");
        assertThat(businesses).contains("CO");
    }

    @Test
    void table_services_finds_serviceIds() {
        Map<String, Object> body = query.tableServices("TB_FW_IMAGE_LOG");
        @SuppressWarnings("unchecked")
        List<String> serviceIds = (List<String>) body.get("serviceIds");
        assertThat(serviceIds).as("body=%s", body).contains("mgcoa8888S0");
    }
}
