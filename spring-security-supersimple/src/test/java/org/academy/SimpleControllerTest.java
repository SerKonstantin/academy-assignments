package org.academy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Base64;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SimpleControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void getPublic() throws Exception {
        mvc.perform(get("/home")).andExpect(status().isOk());
    }

    @Test
    void getPrivateUnauthorized() throws Exception {
        mvc.perform(get("/users")).andExpect(status().isUnauthorized());
    }

    @Test
    void getPrivateAuthorized() throws Exception {
        var request = get("/users")
                .header("Authorization",
                        "Basic " + Base64.getEncoder().encodeToString(("user:user").getBytes()));
        mvc.perform(request).andExpect(status().isOk());
    }

}
