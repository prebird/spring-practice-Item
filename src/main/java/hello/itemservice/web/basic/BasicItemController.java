package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor //final 붙은 필드변수로 생성자를 만들어줌
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model){
        Item findItem = itemRepository.findById(itemId);
        model.addAttribute("item", findItem);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm(){
        System.out.println("Get add called");
        return "basic/addForm";
        //return "basic/item";
    }

    //@PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                       @RequestParam int price,
                       @RequestParam int quantity,
                       Model model){
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);
        itemRepository.save(item);

        //상세화면에서 보여주기용
        model.addAttribute("item", item);

        return "basic/item";
    }

    //@PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item,
                            Model model){
        // Model attribute -> 파라메터를 객체에 자동매핑해준다.

        itemRepository.save(item);

        //상세화면에서 보여주기용
        model.addAttribute("item", item);

        return "basic/item";
    }

    //@PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item){
        // ModelAttribute 이름지정 X
        // -> 객체 클래스의 첫글자를 소문자로 하여서 자동등록

        itemRepository.save(item);

        return "basic/item";
    }

    //@PostMapping("/add")
    public String addItemV4(Item item){
        // ModelAttribute 생략
        itemRepository.save(item);
        return "basic/item";
    }

    @PostMapping("/add")
    public String addItemV5(Item item){
        // ModelAttribute 생략
        itemRepository.save(item);
        return "redirect:/basic/items/"+item.getId();
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model){
        System.out.println("editForm called");
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId,
                       @ModelAttribute Item item){
        itemRepository.updateItem(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }




    /**
     * 테스트 데이터 주가
     */
    @PostConstruct
    public void init(){
        itemRepository.save(new Item("A", 1000, 3));
        itemRepository.save(new Item("B", 2000, 10));

    }
}
