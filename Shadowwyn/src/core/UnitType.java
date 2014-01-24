package core;
import java.util.*;
import java.util.Random;

public enum UnitType
{
	DUMMY(0, "Dummy", 1, 1, 1, 1, false ,false, new Cost(0, 0, 0), "",""),
	WOJAK(1, "Wojak", 1, 2, 15, 4,  false, false, new Cost(50, 0, 0), "image/units/human/tier1.png", "Nienawidzi diabłów. Zwiększone obrażenia w Arcy Diabły.") {
		public float damageRatio(UnitType target)
		{
			if(target.name().equals("Arcy Diabeł"))
				return 1.2f;
			else
				return 1.0f;
		}
	},
	KUSZNIK(2, "Kusznik", 2, 6, 15, 4, true, false, new Cost(190, 0, 0), "image/units/human/tier2.png", "Szansa na zwiększone obrażenia w postacie magiczne.") {
		public float damageRatio(UnitType target)
		{
			int rand = new Random().nextInt(9)+1;
			if(rand < 2)
				return 1.2f;
			else
				return 1.0f;
		}
	},
	MAG_OGNIA(3, "Mag Ognia", 3, 12, 10+10, 4, true, true, new Cost(440, 0, 0), "image/units/human/tier3.png", "Atakuje ognistymi kulami. Zwiększone obrażenia w Enty i Szkielety."){
		public float damageRatio(UnitType target)
		{
			if(target.name().equals("Ent") || target.name().equals("Szkielet"))
				return 1.2f;
			else
				return 1.0f;
		}
	},
	
	RYCERZ_MROKU(4, "Rycerz Mroku", 4, 12, 10+12, 9, false, false, new Cost(600, 0, 0), "image/units/human/tier4.png", "Szarża.Zwiększone obrażenia w jednostki strzelające."){
		public float damageRatio(UnitType target)
		{
			if(target.shooting)
				return 1.2f;
			else
				return 1.0f;
		}
	},
	
	SZKIELET(5, "Szkielet", 1, 3, 2, 4, false, false, new Cost(60, 0, 0), "image/units/demon/tier1.png", "Nekromancja. 50% straconych jednostek powstaje z grobu po walce."),
	POLNOCNICA(6, "Północnica", 2, 6, 5, 5, false, false, new Cost(180, 0, 0), "image/units/demon/tier2.png", "Mała szansa na uniknięcie otrzymania obrażeń od jednostek strzelających.") {
		public float receivedDamageRation(UnitType from)
		{
			int c = new Random().nextInt(100);
			if(c>95 && from.shooting)
				return 0.0f;
			else
				return 1.0f;
		}
	},
	WAPIERZ(7, "Wąpierz", 3, 10, 9, 6, false, true, new Cost(400, 0, 0), "image/units/demon/tier3.png", "Wysysa punkty życia."),
	ARCY_DIABEL(8, "Arcy Diabeł", 4, 16, 16, 10, false, true, new Cost(900, 0, 0), "image/units/demon/tier4.png", "Nienawidzi drzew. Zwiększone obrażenia w Enty."){
		public float damageRatio(UnitType target)
		{
			if(target.name().equals("Ent"))
				return 1.2f;
			else
				return 1.0f;
		}
	},
	
	NIMFA(9, "Nimfa", 1, 3, 1, 6, false, true, new Cost(30, 0, 0), "image/units/forest/tier1.png", "Postać magiczna. Otrzymuje mniejsze obrażenia od jednostek magicznych."){
		public float receivedDamageRation(UnitType from)
		{
			if(from.magic)
				return 0.8f;
			else
				return 1.0f;
		}
	},
	ELFI_SKRYTOBOJCA(10, "Elfi Skrytobójca", 2, 6, 4, 5, false, false, new Cost(210, 0, 0), "image/units/forest/tier2.png", "Mała szansa na podwójne uderzenie."){
		public float damageRatio(UnitType target)
		{
			int rand = new Random().nextInt(100)+1;
			if(rand>90)
				return 2.0f;
			else
				return 1.0f;
		}
	},
	ENT(11, "Ent", 3, 11, 13, 4, false, false, new Cost(480, 0, 0), "image/units/forest/tier3.png", "Zmniejszone obrażenia od jednostek strzelających."){
		public float receivedDamageRation(UnitType from)
		{
			if(from.shooting)
				return 0.7f;
			else
				return 1.0f;
		}
	},
	FENIKS(12, "Feniks", 4, 18, 8, 10, false, true, new Cost(800, 0, 0), "image/units/forest/tier4.png","Ognisty oddech. Zwiększone obrażenia w Wąpierze."){
		public float damageRatio(UnitType target)
		{
			if(target.name().equals("Wąpierz"))
				return 1.1f;
			else
				return 1.0f;
		}
	};
	
	public final int id;
	public final String name;
	
	public CastleType castle = null;
	public final int level;
	public final int attack;
	public final int defense;
	public final int speed;
	public final boolean shooting;
	public final boolean magic;
	public final Cost cost;
	public final String file;
	public final String opis;
	
	private UnitType(int i, String n, int l, int a, int d, int s, boolean sh, boolean mg, Cost co, String f, String op)
	{
		id = i;
		name = n;
		level = l;
		attack = a;
		defense = d;
		speed = s;
		shooting = sh;
		magic = mg;
		cost = co;
		file = f;
		opis = op;
	}
	
	private static final Map<Integer, UnitType> mapId = new HashMap<Integer, UnitType>();
	private static final Map<String, UnitType> mapName = new HashMap<String, UnitType>();
	
	static
	{
		for (UnitType i: UnitType.values()) {
			mapId.put(i.id, i);
			mapName.put(i.name, i);
		}
	}
	
	public float damageRatio(UnitType target)
	{
		return 1f;
	}
	
	public float receivedDamageRation(UnitType from)
	{
		return 1f;
	}
	
	public static UnitType getTier(int tier, CastleType type)
	{
		for (UnitType i: UnitType.values()) {
			System.out.println ("unit "+i.level+", "+i.castle);
			if (i.level == tier && i.castle == type) {
				return i;
			}
		}
		return null;
	}
	
	public static UnitType fromId(int id)
	{
		return mapId.get(id);
	}
	
	public static UnitType fromName(String name)
	{
		return mapName.get(name);
	}
 }