package com.vttp.miniproject.Project;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MVCTests {
    @Autowired
    private MockMvc mockMvc;

    // test login
    @Test
    void accessWithLogin() {
        MockHttpSession session = new MockHttpSession();
        RequestBuilder req = MockMvcRequestBuilders.post("/authenticate")
                .accept(MediaType.TEXT_HTML)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", "test")
                .session(session);

        MvcResult result = null;
        try {
            result = mockMvc.perform(req).andReturn();
        } catch (Exception e) {
            fail("Cannot perform mock request", e);
        }

        MockHttpServletResponse resp = result.getResponse();
        assertEquals(302, resp.getStatus());
        assertEquals("test", session.getAttribute("username"));
        assertEquals("/auth/search", resp.getRedirectedUrl());
    }

    @Test
    void directAccessWithLogin() {
        MockHttpServletResponse resp = getRespWithLogin(
                true,
                "/auth/search");
        assertEquals(200, resp.getStatus());
    }

    @Test
    void accessWithoutLogin() {
        MockHttpServletResponse resp = getRespWithLogin(
                false,
                "/auth/search");
        assertEquals(302, resp.getStatus());
        assertEquals("/", resp.getRedirectedUrl());
    }

    private MockHttpServletResponse getRespWithLogin(
            boolean loggedIn,
            String path) {
        MockHttpSession session = new MockHttpSession();
        if (loggedIn) {
            session.setAttribute("username", "test");
        }
        RequestBuilder req = MockMvcRequestBuilders.get(path)
                .accept(MediaType.TEXT_HTML)
                .session(session);
        MvcResult result = null;
        try {
            result = mockMvc.perform(req).andReturn();
        } catch (Exception e) {
            fail("Cannot perform mock request", e);
        }

        MockHttpServletResponse resp = result.getResponse();
        return resp;
    }

    @Test
    void logout() {
        MockHttpServletResponse resp = getRespWithLogin(
                true,
                "/logout");
        assertEquals(200, resp.getStatus());
    }

    // test search
    @Test
    void randomSearch() {
        MockHttpServletResponse resp = getRespWithLogin(
                true,
                "/auth/results");
        assertEquals(200, resp.getStatus());
    }

    @Test
    void keywordSearch() {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("username", "test");
        RequestBuilder req = MockMvcRequestBuilders.post("/auth/results")
                .accept(MediaType.TEXT_HTML)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("keyword", "korean")
                .session(session);

        MvcResult result = null;
        try {
            result = mockMvc.perform(req).andReturn();
        } catch (Exception e) {
            fail("Cannot perform mock request", e);
        }

        MockHttpServletResponse resp = result.getResponse();
        assertEquals(200, resp.getStatus());
    }
    // test individual listing

    @Test
    void listingViewPass() {
        MockHttpServletResponse resp = getRespWithLogin(
                true,
                "/auth/listing/00526f9f48db7484243b5c09d2d1daa09ff");
        assertEquals(200, resp.getStatus());
    }

    @Test
    void listingViewFail() {
        MockHttpServletResponse resp = getRespWithLogin(
                true,
                "/auth/listing/1");
        assertEquals(400, resp.getStatus());
    }

    @Test
    void favouritesView() {
        MockHttpServletResponse resp = getRespWithLogin(
                true,
                "/auth/favourites");
        assertEquals(200, resp.getStatus());
    }

    @Test
    void postCommentFailNoBody() {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("username", "test");
        session.setAttribute("uuid", "00526f9f48db7484243b5c09d2d1daa09ff");

        RequestBuilder req = MockMvcRequestBuilders.post("/auth/listing/00526f9f48db7484243b5c09d2d1daa09ff")
                .accept(MediaType.TEXT_HTML)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("commentBody", "")
                .param("rating", "5")
                .session(session);
        MvcResult result = null;
        try {
            result = mockMvc.perform(req).andReturn();
        } catch (Exception e) {
            fail("Cannot perform mock request", e);
        }
        MockHttpServletResponse resp = result.getResponse();
        assertEquals(400, resp.getStatus());
    }

    @Test
    void addToFavouritesFailAlreadyInside() {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("username", "test");
        session.setAttribute("uuid", "00526f9f48db7484243b5c09d2d1daa09ff");

        RequestBuilder req = MockMvcRequestBuilders.post("/auth/favourites")
                .accept(MediaType.TEXT_HTML)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("uuid", "00526f9f48db7484243b5c09d2d1daa09ff")
                .session(session);
        MvcResult result = null;
        try {
            result = mockMvc.perform(req).andReturn();
        } catch (Exception e) {
            fail("Cannot perform mock request", e);
        }
        MockHttpServletResponse resp = result.getResponse();
        assertEquals(400, resp.getStatus());
    }

    @Test
    void addToFavouritesFailNoFormContent() {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("username", "test");
        session.setAttribute("uuid", "00526f9f48db7484243b5c09d2d1daa09ff");

        RequestBuilder req = MockMvcRequestBuilders.post("/auth/favourites")
                .accept(MediaType.TEXT_HTML)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("uuid", "")
                .session(session);
        MvcResult result = null;
        try {
            result = mockMvc.perform(req).andReturn();
        } catch (Exception e) {
            fail("Cannot perform mock request", e);
        }
        MockHttpServletResponse resp = result.getResponse();
        assertEquals(400, resp.getStatus());
    }

    @Test
    void addToFavouritesFailUsernameCorrupt() {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("username", "test1");
        session.setAttribute("uuid", "00526f9f48db7484243b5c09d2d1daa09ff");

        RequestBuilder req = MockMvcRequestBuilders.post("/auth/favourites")
                .accept(MediaType.TEXT_HTML)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("uuid", "00526f9f48db7484243b5c09d2d1daa09ff")
                .session(session);
        MvcResult result = null;
        try {
            result = mockMvc.perform(req).andReturn();
        } catch (Exception e) {
            fail("Cannot perform mock request", e);
        }
        MockHttpServletResponse resp = result.getResponse();
        assertEquals(400, resp.getStatus());
    }
}
