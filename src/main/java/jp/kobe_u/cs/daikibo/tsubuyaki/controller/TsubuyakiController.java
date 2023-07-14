package jp.kobe_u.cs.daikibo.tsubuyaki.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.kobe_u.cs.daikibo.tsubuyaki.entity.Tsubuyaki;
import jp.kobe_u.cs.daikibo.tsubuyaki.service.TsubuyakiService;

@Controller

public class TsubuyakiController {

    @Autowired

    TsubuyakiService ts;

    // タイトル画面を表示

    @GetMapping("/")

    String showIndex() {

        return "index";

    }

    // メイン画面を表示

    @GetMapping("/read")

    String showTsubuyakiList(Model model) {

        List<Tsubuyaki> list = ts.getAllTsubuyaki(); // 全つぶやきを取得

        model.addAttribute("tsubuyakiList", list); // モデル属性にリストをセット

        model.addAttribute("tsubuyakiResearchForm", new TsubuyakiResearchForm()); // 空フォームをセット

        model.addAttribute("tsubuyakiForm", new TsubuyakiForm()); // 空フォームをセット

        return "tsubuyaki_list"; // リスト画面を返す

    }

    // つぶやきを投稿

    @PostMapping("/read")

    String postTsubuyaki(@ModelAttribute("tsubuyakiForm") TsubuyakiForm form, Model model) {

        // フォームからエンティティに移し替え

        Tsubuyaki t = new Tsubuyaki();

        t.setName(form.getName());

        t.setComment(form.getComment());

        // サービスに投稿処理を依頼

        ts.postTsubuyaki(t);

        return "redirect:/read"; // メイン画面に転送

    }

    // つぶやきをキーワードで検索する

    @GetMapping("/search")

    String showTsubuyakiReseachList(Model model, @ModelAttribute @Validated TsubuyakiResearchForm form,
            @RequestParam String comment) {

        List<Tsubuyaki> list = ts.getAllResearchTsubuyaki(comment); // 全つぶやきを取得

        model.addAttribute("tsubuyakiList", list); // モデル属性にリストをセット

        return "tsubuyaki_research_list"; // リスト画面を返す

    }

}
