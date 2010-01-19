package com.easymorse.weapons.server;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WeaponService {

	private static List<Weapon> data;

	static {
		data = new ArrayList<Weapon>();

		Weapon weapon = new Weapon();
		weapon.setId("1");
		weapon.setName("虎式坦克");
		weapon.setDescription("w1.desc");
		weapon.setImageUrl("w1.png");
		data.add(weapon);

		weapon = new Weapon();
		weapon.setId("2");
		weapon.setName("T-34 坦克");
		weapon.setDescription("w2.desc");
		weapon.setImageUrl("w2.png");
		data.add(weapon);

		// "T-34 坦克",
		// "虎式坦克","零式战斗轰炸机","野马战斗机","B-25轰炸机"
	}

	@RequestMapping("/list.json")
	@ModelAttribute("data")
	public List list() {
		return data;
	}
}
