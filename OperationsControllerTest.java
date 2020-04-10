


import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = MyWebConfig.class)
public class OperationsControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        this.mockMvc = builder.build();
    }

    @Test
    public void testCalculateExpression() throws Exception {
    	
    	String sm = "";
		if(checkParenthesesDepth(request.getParameter("expression")) == -1)
			sm = "Invalid arithmetic expression. Please check the parentheses.";
		else
			sm = String.valueOf(evaluateInfix(request.getParameter("expression")));
        map.addObject("message", sm);
        
        HttpSession session = request.getSession();
        String str = String.valueOf(request.getAttribute("oldExpressions"));
        StringBuilder strb = new StringBuilder(str);
        strb.append(" | "+sm);
        str = strb.toString();
        session.setAttribute("oldExpressions", strb.toString());
        
        String[] strList = str.split("|");
        map.addObject("lists", strList);
        
        ResultMatcher ok = MockMvcResultMatchers.status().isOk();
        ResultMatcher msg = MockMvcResultMatchers.model()
                            .attribute("msg", strList);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/");
        this.mockMvc.perform(builder)
               .andExpect(ok)
               .andExpect(msg);
    }
}

