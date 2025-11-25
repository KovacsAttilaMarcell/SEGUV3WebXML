package seguv3JSON;

import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.Set;

public class JSONValidationSEGUV3 {

    public static void main(String[] args) throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V202012);

        JsonNode json = mapper.readTree(new File("orarendSEGUV3.json"));
        JsonNode schemaNode = mapper.readTree(new File("orarendSEGUV3Schema.json"));

        JsonSchema schema = factory.getSchema(schemaNode);

        Set<ValidationMessage> errors = schema.validate(json);

        if (errors.isEmpty()) {
            System.out.println("VALID JSON – A dokumentum megfelel a sémának!");
        } else {
            System.out.println("Hibák találhatók:");
            errors.forEach(e -> System.out.println(" - " + e.getMessage()));
        }
    }
}
