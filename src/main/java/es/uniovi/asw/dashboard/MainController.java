package es.uniovi.asw.dashboard;


import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import es.uniovi.asw.comparator.DateComparator;
import es.uniovi.asw.comparator.PopularityComparator;
import es.uniovi.asw.comparator.RatioComparator;
import es.uniovi.asw.dashboard.dao.CommentDao;
import es.uniovi.asw.dashboard.dao.ProposalDao;
import es.uniovi.asw.dashboard.dao.UserDao;
import es.uniovi.asw.dashboard.dao.VoteDao;
import es.uniovi.asw.model.Comment;
import es.uniovi.asw.model.Proposal;
import es.uniovi.asw.model.User;
import es.uniovi.asw.model.filtrable.Filtrable;

@Controller
public class MainController {
	private User loggedUser;
//    @Autowired
//    private KafkaProducer kafkaProducer;
    private List<Proposal> proposals;
    private Proposal selectedProposal;
    private Comment selectedComment;
    private Proposal p;
    private Comparator<Filtrable> comparator;
    public static boolean refresh = false;
    @ModelAttribute("Comment")
    public Comment getComment() {
    	return new Comment();
    }
    
    @ModelAttribute("Proposal")
    public Proposal getProposal() {
    	return new Proposal();
    }
    @RequestMapping("/")
    public ModelAndView landing(Model model) {
    	//model.addAttribute("message", new Message());
    	ModelAndView model2 = new ModelAndView("login");
    	model.addAttribute("id", "");
    	model.addAttribute("password", "");
    	model.addAttribute("errorMsg", "");
        return model2;
    }
    
    /*
    @RequestMapping("/send")
    public String send(Model model, @ModelAttribute Message message) {
        kafkaProducer.send("exampleTopic", message.getMessage());
        return "redirect:/";
    }*/
	
    @RequestMapping("/select/{id}")
    public String select(@PathVariable("id") String id, @ModelAttribute("id") Integer uid, RedirectAttributes request){
    	selectedProposal = ProposalDao.GetProposalByID(Integer.parseInt(id));
    	request.addAttribute("id", loggedUser.getId());
    	request.addAttribute("password", loggedUser.getPassword());
    	return "redirect:../showAddProposals";
    }
    @RequestMapping("/deselect/Comm")
    public String deselectComment(RedirectAttributes request){
    	selectedComment = null;
    	request.addAttribute("id", loggedUser.getId());
    	request.addAttribute("password", loggedUser.getPassword());
    	return "redirect:../commentProposal/" + p.getId();
    }
    
    @RequestMapping("/deselect/")
    public String deselect(RedirectAttributes request){
    	selectedProposal = null;
    	request.addAttribute("id", loggedUser.getId());
    	request.addAttribute("password", loggedUser.getPassword());
    	return "redirect:../showAddProposals";
    }
    
    @RequestMapping("/selectComment/{id}")
    public String selectComment(@PathVariable("id") String id, @ModelAttribute("id") Integer uid, RedirectAttributes request){
    	List<Filtrable> cmnt = p.getComments();
    	for(Filtrable fl : cmnt)
    		if(fl.getId() == Integer.parseInt(id))
    			selectedComment = (Comment)fl;
    	return "redirect:../commentProposal/" + p.getId();
    }
    @RequestMapping("/showAddProposals")
    public ModelAndView showAddProposals(@RequestParam(value = "id") String uId, @RequestParam(value = "password") String uPass, 
    									Model model, HttpServletRequest request,	HttpServletResponse response) {
    	System.out.println(uId + " - " + uPass);
    	if(uId.isEmpty()) return new ModelAndView("login");
    	User user = UserDao.getUserLog(Integer.parseInt(uId), uPass);
    	ModelAndView mdv = new ModelAndView("showAddProposals");
    	mdv.addObject("user", user);
    	mdv.addObject("proposals", ProposalDao.getAllProposals());
    	if(selectedProposal == null) {
    		selectedProposal = new Proposal();
    		for(Proposal prop : ProposalDao.getAllProposals()) {
    			for(User usr : prop.getPositiveVotes() )
    				selectedProposal.AddPositive(usr);
    			for(User usr : prop.getNegativeVotes())
    				selectedProposal.AddNegative(usr);
    		}
    	}
    	mdv.addObject("selected", selectedProposal);
    	
		if (user == null)
			return new ModelAndView("login");
		else {
			loggedUser = user;
			return mdv;
		}
    }
    
    @RequestMapping("/commentProposal/{id}")
    //move to commentProposal.html
    public ModelAndView commentProposal(@PathVariable("id") String id, Model model){
    	p = ProposalDao.GetProposalByID(Integer.parseInt(id));
    	ModelAndView mav = new ModelAndView("commentProposal");
    	System.out.println(p);
    	model.addAttribute("p", p);
    	mav.addObject("p", p);
    	if(selectedComment == null) {
    		selectedComment = new Comment();
    		
    		for(Proposal prop : ProposalDao.getAllProposals()) {
    			for(Filtrable cf : prop.getComments()) {
    				for(User usr : cf.getPositiveVotes())
    					selectedComment.AddPositive(usr);
    				for(User usr : cf.getNegativeVotes())
    					selectedComment.AddNegative(usr);
    			}
    		}
    	}
    	mav.addObject("selected", selectedComment);
    	return mav;
    }
    
    @RequestMapping("/upvoteProposal/{id}")
    public String upvoteProposal(@PathVariable("id") String id, @ModelAttribute("id") Integer uid, RedirectAttributes request){
    	VoteDao.InsertVotesProp(Integer.parseInt(id), loggedUser.getId(), 1);
    	request.addAttribute("id", loggedUser.getId());
    	request.addAttribute("password", loggedUser.getPassword());
    	return "redirect:../showAddProposals";
    }
    
    @RequestMapping("/downvoteProposal/{id}")
    public String downvoteProposal(@PathVariable("id") String id, @ModelAttribute("id") Integer uid, RedirectAttributes request){
    	VoteDao.InsertVotesProp(Integer.parseInt(id),  loggedUser.getId(), 0);
    	request.addAttribute("id", loggedUser.getId());
    	request.addAttribute("password", loggedUser.getPassword());
    	return "redirect:../showAddProposals";
    }
    
    @RequestMapping("/createProposal")
    public String createProposal(@ModelAttribute("Proposal") Proposal proposal, @RequestParam(value="title") String title, @RequestParam(value="text") String text,
    							@RequestParam(value="category") String category, Model model, RedirectAttributes request){
    	Proposal prp = new Proposal(loggedUser, title, category, text);
    	ProposalDao.save(prp);
    	request.addAttribute("id", loggedUser.getId());
    	request.addAttribute("password", loggedUser.getPassword());
    	return "redirect:showAddProposals";
    }
    
    @RequestMapping("/createComment/{id}")
    // {id} proposal ID
    public String createComment(@ModelAttribute("Comment") Comment comment, @PathVariable("id") String proposalID, @RequestParam(value="text") String text){
    	Comment com = new Comment(loggedUser,ProposalDao.GetProposalByID(Integer.parseInt(proposalID)), text);
    	CommentDao.save(com);
    	return "redirect:/commentProposal/" + proposalID;
    }
    
    @RequestMapping("/orderByDate/") 
    public String orderByDate(RedirectAttributes request){
    	comparator = new DateComparator();
    	request.addAttribute("id", loggedUser.getId());
    	request.addAttribute("password", loggedUser.getPassword());
    	return "redirect:../showAddProposals";
    }
    
    @RequestMapping("/orderByPopularity/") 
    public String orderByPopularity(RedirectAttributes request){
    	comparator = new PopularityComparator();
    	request.addAttribute("id", loggedUser.getId());
    	request.addAttribute("password", loggedUser.getPassword());
    	return "redirect:../showAddProposals";
    }
    
    @RequestMapping("/orderByRatio/") 
    public String orderByRatio(RedirectAttributes request){
    	comparator = new RatioComparator();
    	request.addAttribute("id", loggedUser.getId());
    	request.addAttribute("password", loggedUser.getPassword());
    	return "redirect:../showAddProposals";
    }
    
    @RequestMapping("/orderNone/") 
    public String orderNone(RedirectAttributes request){
    	comparator = null;
    	request.addAttribute("id", loggedUser.getId());
    	request.addAttribute("password", loggedUser.getPassword());
    	return "redirect:../showAddProposals";
    }
    
    @RequestMapping("/upvoteComment/{id}")
    public String upvoteComment(@PathVariable("id") String id, @ModelAttribute("id") Integer uid, RedirectAttributes request){
    	VoteDao.InsertVotesCom(Integer.parseInt(id),  loggedUser.getId(), 1);
    	request.addAttribute("id", loggedUser.getId());
    	request.addAttribute("password", loggedUser.getPassword());
    	return "showAddProposals";
    }
    
    @RequestMapping("/downvoteComment/{id}")
    public String downvoteComment(@PathVariable("id") String id, @ModelAttribute("id") Integer uid, RedirectAttributes request){
    	VoteDao.InsertVotesCom(Integer.parseInt(id),  loggedUser.getId(), 0);
    	request.addAttribute("id", loggedUser.getId());
    	request.addAttribute("password", loggedUser.getPassword());
    	return "showAddProposals";
    }
    
    @ModelAttribute("allProposals")
    public List<Proposal> getAllProposals(){
    	new ProposalDao();
   		proposals = ProposalDao.getAllProposals();
    	if(comparator != null) Collections.sort(proposals, comparator);
    	return proposals;
    }
    
    @ModelAttribute("refresh")
    public boolean getRefresh() {
    	if(!refresh) return false;
    	refresh = false;
    	return true;
    }
    
}