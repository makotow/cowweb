package jp.gr.java_conf.cowweb;

import com.github.ricksbrown.cowsay.Cowsay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Remote cowsay invoker using Spring Boot
 *
 * @author hhiroshell
 */
@Controller
@RequestMapping("/")
public class CowsayController {

    private static final String br = System.getProperty("line.separator");

    private static final List<String> cowfiles;

    @Autowired
    AccessCounter counter;

    static {
//        List<String> infelicities = Arrays.asList(new String[]{"head-in", "telebears", "sodomized"});
        List<String> c = new ArrayList<>();
//        Arrays.stream(Cowsay.say(new String[]{"-l"}).split(br)).forEach(f -> {
        Arrays.stream(Cowsay.say(new String[]{"-l"})).forEach(f -> {
//            if (!infelicities.contains(f)) {
                c.add(f);
//            }
        });
        cowfiles = Collections.unmodifiableList(c);
    }

    /*
     * Return cowsay's 'say' message.
     *
     * @return Cowsay's 'say' message.
     */
    @RequestMapping("/cowsay")
    public String say(@RequestParam(required = false) Optional<String> message, Model model) {
        model.addAttribute("msg", Cowsay.say(new String[]{"-f", getRandomCowfile(), message.orElse("Moo!")}));
        return "say";
//        return Cowsay.say(new String[]{"-f", getRandomCowfile(), message.orElse("Moo!")});
    }

    private static String getRandomCowfile() {
        return cowfiles.get(new Random().nextInt(cowfiles.size()));
    }
}
