package com.easymorse.weapons.server;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WeaponService {

	private static List<Weapon> data;

	static {
		data = new ArrayList<Weapon>();

		Weapon weapon = new Weapon();
		weapon.setId("1");
		weapon.setName("虎式坦克");
		weapon.setDescription("虎式重型坦克即“虎I”坦克是第二次世界大战期间纳粹德国制造的重型坦克。");
		weapon.setImageUrl("w1.png");
		data.add(weapon);

		weapon = new Weapon();
		weapon.setId("2");
		weapon.setName("T-34 坦克");
		weapon.setDescription("T-34坦克是苏联于1940年代到1950年代生产的中型坦克。");
		weapon.setImageUrl("w2.png");
		data.add(weapon);
	}

	@RequestMapping("/list.json")
	@ModelAttribute("data")
	public List<Weapon> list() {
		return data;
	}

	@RequestMapping(value = "/delete.json", method = RequestMethod.POST)
	public String delete(@RequestParam("id") List<String> ids) {
		for (String id : ids) {
			data.remove(find(id));
		}
		return "deleted";
	}

	private Weapon find(String id) {
		for (Weapon w : data) {
			if (w.getId().equals(id)) {
				return w;
			}
		}
		return null;
	}

	@RequestMapping(value = "/save.json", method = RequestMethod.POST)
	public String save(Weapon weapon) {

		if (weapon.getId() == null || weapon.getId().isEmpty()) {
			create(weapon);
		} else {
			update(weapon);
		}

		return "saved";
	}

	private void update(Weapon weapon) {
		Weapon w = find(weapon.getId());
		if (w != null) {
			w.setName(weapon.getName());
			w.setDescription(weapon.getDescription());
			w.setImageUrl(weapon.getImageUrl());
		}
	}

	@RequestMapping("/get.json")
	@ModelAttribute("weapon")
	public Weapon get(@RequestParam("id") String id) {
		return find(id);
	}

	private void create(Weapon weapon) {
		weapon.setId(Integer.toString(data.size() + 1));
		data.add(weapon);
	}
}
