package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.testutils.UimaTests.assertResultsContains;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTestCas;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.io.InputStream;
import java.util.Collection;

import org.apache.commons.io.IOUtils;
import org.apache.uima.jcas.JCas;
import org.junit.Test;

import ch.epfl.bbp.uima.types.LinnaeusSpecies;

public class LinnaeusAnnotatorTest {

    @Test
    public void test() throws Exception {
        String file = "/linnaeus/pmcA1239921.txt";
        InputStream stream = getClass().getResourceAsStream(file);
        String testtext = IOUtils.toString(stream);

        JCas jCas = getTestCas(testtext);

        createEngine(LinnaeusAnnotator.class).process(jCas);

        Collection<LinnaeusSpecies> sps = select(jCas, LinnaeusSpecies.class);

        assertResultsContains(sps, "mostProbableSpeciesId", "species:ncbi:9606");
        assertResultsContains(sps, "mostProbableSpeciesId", "species:ncbi:9913");
    }

    @Test
    public void test2() throws Exception {

        // FIXME rodent not found
        // JCas jCas = getTestCas("bla bla rodent bla bla");
        JCas jCas = getTestCas("bla bla rat bla bla");

        createEngine(LinnaeusAnnotator.class).process(jCas);

        Collection<LinnaeusSpecies> sps = select(jCas, LinnaeusSpecies.class);

        assertResultsContains(sps, "mostProbableSpeciesId",
                "species:ncbi:10116");
    }
}
