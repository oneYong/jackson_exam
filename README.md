## jackson-lib 를 이용한 Spring Example

	[환경]
	java 1.8
	tomcat 8.0.3
	spring 4.2.4
	
### HomeController - 인덱스 페이지

	 // home 화면
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() throws Exception{
        logger.info("Come on!! Baby!!");
        return "index";
    }
    
### HomeControllerTest - 인덱스 페이지 테스트
	
	 @Before
    public void init(){
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        this.mockMvc = MockMvcBuilders.standaloneSetup(homeController)
                .addFilter(filter).build();
    }

    @Test
    public void 루트매핑_테스트()throws Exception{
        this.mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andDo(print()).andExpect(status().isOk());
    }
    
### HomeController - examModelAttribute
	
    // parameters 를 여러개를 담아서 처리할
    @RequestMapping(value = "/exam/ModelAttribute", method = RequestMethod.GET)
    @ResponseBody
    public List<User> examModelAttribute(@ModelAttribute SearchDTO searchParam) throws Exception{
        logger.info(searchParam.toString());

        // user info setting
        List<User> userList = new ArrayList<User>();
        userList.add(new User("iersans1","원디1"));
        userList.add(new User("iersans2","원디2"));

        return userList;
    }
    
### HomeControllerTest - examModelAttribute Test

	@Test
    public void GET방식_ModelAttribute_테스트()throws Exception{
        MultiValueMap<String,String> params
                = new LinkedMultiValueMap<String,String>();

        params.add("userId","iersans");
        params.add("userName","원디");

        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/exam/ModelAttribute")
                        .accept(MediaType.APPLICATION_JSON)
                        .params(params))
                .andDo(print()).andExpect(status().isOk());
    }
    
    
### HomeController - examRequestParam

	// parameters 한개 또는 여러개 ... 여러개는 여려개를 나열하면됨 ^^
    // @RequestParam 어노테이션이 적용된 파라미터는 기본적으로 필수 파라미터!!
    // 따라서, 명시한 파라미터가 존재하지 않을 경우 400 에러가 발생!!
    // [Exam]==========================================================
    // @RequestParam(value="userId", defaultValue = "0") Long userId,
    // @RequestParam(value="type, required=false) String type
    @RequestMapping(value = "/exam/RequestParam", method = RequestMethod.GET)
    @ResponseBody
    public User examRequestParam(@RequestParam(value="userName", required = false) String userName) throws Exception{
        logger.info("userName is :" + userName);

        return new User("iersans1","원디1");
    }
    
### HomeControllerTest - examRequestParam Test

    @Test
    public void GET방식_RequestParam_테스트()throws Exception{
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/exam/RequestParam")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("userName","원디"))
                .andDo(print()).andExpect(status().isOk());
    }
    
### HomeController - examRestfulGet 
	 @RequestMapping(value = "/exam/rest/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public User examRestfulGet(@PathVariable("userId") String userId) throws Exception{
        logger.info("userId is :" + userId);

        return new User("iersans1","원디1");
    }
    
### HomeControllerTest - examRestfulGet Test 

	@Test
    public void GET방식_Restful방식_테스트()throws Exception{
        MockHttpServletRequestBuilder requestBuilder
                = MockMvcRequestBuilders
                        .get("/exam/rest/iersans")
                        .accept(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userId",is("iersans1")))
                .andExpect(jsonPath("$.userName",is("원디1")));
    }
    
    
### HomeController - examRequestBodyPost
	    
	@RequestMapping(value = "/exam/requestBody/post", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public User examRequestBodyPost(@RequestBody User user) throws Exception{
        logger.info("user is " + user);

        // save ...

        return user;
    }
    
### HomeControllerTest - examRequestBodyPost Test

	@Test
    public void POST방식_RequestBody_테스트()throws Exception{
        User user = new User("testUser","관리자");

        // java to json
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(user);

        logger.debug("content = {}", content);

        MockHttpServletRequestBuilder requestBuilder
                = MockMvcRequestBuilders
                .post("/exam/requestBody/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
    
### HomeController - examRequestBodyPut

	@RequestMapping(value = "/exam/requestBody/put/{userId}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public User examRequestBodyPut(@RequestBody User user
                                   ,@PathVariable String userId) throws Exception{
        logger.info("user is " + user);
        logger.info("userId is " + userId);

        // modify...

        return user;
    }
    
### HomeControllerTest - examRequestBodyPut Test

	@Test
    public void PUT방식_RequestBody_테스트()throws Exception{
        User user = new User("iersans","원드래곤");

        // java to json
        ObjectMapper mapper = new ObjectMapper();
        String content = mapper.writeValueAsString(user);

        logger.debug("content = {}", content);

        MockHttpServletRequestBuilder requestBuilder
                = MockMvcRequestBuilders
                .put("/exam/requestBody/put/iersans")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userId",is("iersans")))
                .andExpect(jsonPath("$.userName",is("원드래곤")));
    }
    
### HomeController - examRestfulDelete

	
    @RequestMapping(value = "/exam/rest/delete/{userId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public User examRestfulDelete(@PathVariable String userId) throws Exception{
        logger.info("userId is " + userId);

        // delete...

        return new User("iersans","원디");
    }
    
### HomeControllerTest - examRestfulDelete Test

	@Test
    public void DELETE방식_Restful_테스트()throws Exception{


        MockHttpServletRequestBuilder requestBuilder
                = MockMvcRequestBuilders
                .delete("/exam/rest/delete/iersans")
                .accept(MediaType.APPLICATION_JSON);

        this.mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userId",is("iersans")));
    }