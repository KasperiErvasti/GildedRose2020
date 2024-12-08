package fi.oulu.tol.sqat.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import fi.oulu.tol.sqat.GildedRose;
import fi.oulu.tol.sqat.Item;

public class GildedRoseTest {

	@Test
	public void testTheTruth() {
		assertTrue(true);
	}
	@Test
	public void exampleTest() {
		//create an inn, add an item, and simulate one day
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("+5 Dexterity Vest", 10, 20));
		inn.oneDay();
		
		//access a list of items, get the quality of the one set
		List<Item> items = inn.getItems();
		int quality = items.get(0).getQuality();
		
		//assert quality has decreased by one
		assertEquals("Failed quality for Dexterity Vest", 19, quality);
	}
	
	@Test
	public void test_SellInDecrese() {
		//create an inn, add an item, and simulate one day
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("+5 Dexterity Vest", 10, 20));
		inn.oneDay();
		
		//access a list of items, get the sellIn of the one set
		List<Item> items = inn.getItems();
		int sellIn = items.get(0).getSellIn();
		
		//assert sellIn has decreased by one
		assertEquals("Failed sellIn for Dexterity Vest", 9, sellIn);
	}
	
	@Test
    public void test_BrieAging() {
        GildedRose inn = new GildedRose();
        inn.setItem(new Item("Aged Brie", 3, 10));
        inn.oneDay();
        inn.oneDay();

        List<Item> items = inn.getItems();
        int quality = items.get(0).getQuality();

        assertEquals("Failed quality for Aged Brie", 12, quality);
    }

    @Test
    public void test_BrieAgingOver50() {
        GildedRose inn = new GildedRose();
        inn.setItem(new Item("Aged Brie", 1, 47));
        inn.oneDay();
        inn.oneDay();
        inn.oneDay();
        inn.oneDay();

        List<Item> items = inn.getItems();
        int quality = items.get(0).getQuality();

        assertEquals("Failed quality for Aged Brie", 50, quality);
    }
	
	@Test
	public void test_AfterSellInQuality() {
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("Elixir of the Mongoose", 0, 20));
		inn.oneDay();
		inn.oneDay();
		
		List<Item> items = inn.getItems();
		int quality = items.get(0).getQuality();
		
		assertEquals("Failed quality for Elixir of the Mongoose", 16, quality);
	}
	

	@Test
	public void test_QualityNotNegative() {
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("Dagger", 1, 1));
		inn.oneDay();
		inn.oneDay();
		inn.oneDay();
		
		List<Item> items = inn.getItems();
		int quality = items.get(0).getQuality();
		
		assertEquals("Failed quality for Dexterity Vest", 0, quality);
	}
	
	
	@Test
	public void test_BackstageQualityIncrease() {
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("Backstage passes to a TAFKAL80ETC concert", 11, 1));
		inn.oneDay(); //11, +1 = 2
		inn.oneDay(); //10, +2 = 4
		inn.oneDay(); //9,  +2 = 6
		inn.oneDay(); //8,  +2 = 8
		inn.oneDay(); //7,  +2 = 10
		inn.oneDay(); //6,  +2 = 12
		inn.oneDay(); //5,  +3 = 15
		
		List<Item> items = inn.getItems();
		int quality = items.get(0).getQuality();
		
		assertEquals("Failed quality for Backstage passes to a TAFKAL80ETC concert", 15, quality);
	}
	
	@Test
	public void test_BackstageQualityAt0Sellin() {
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("Backstage passes to a TAFKAL80ETC concert", 2, 50));
		inn.oneDay();
		inn.oneDay();
		inn.oneDay();

		
		List<Item> items = inn.getItems();
		int quality = items.get(0).getQuality();
		
		assertEquals("Failed quality for Backstage passes to a TAFKAL80ETC concert", 0, quality);
	}

	@Test
	public void test_BackstageOver50() {
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("Backstage passes to a TAFKAL80ETC concert", 100, 49));
		inn.oneDay();
		inn.oneDay();
		inn.oneDay();

		
		List<Item> items = inn.getItems();
		int quality = items.get(0).getQuality();
		
		assertEquals("Failed quality for Backstage passes to a TAFKAL80ETC concert", 50, quality);
	}
	
	
	@Test
	public void test_Sulfuras() {
		GildedRose inn = new GildedRose();
		inn.setItem(new Item("Sulfuras, Hand of Ragnaros", 100, 80));
		inn.oneDay();
		inn.oneDay();
		inn.oneDay();

		
		List<Item> items = inn.getItems();
		int quality = items.get(0).getQuality();
		int sellIn = items.get(0).getSellIn();
		
		assertEquals("Failed quality for Sulfuras, Hand of Ragnaros", 80, quality);
		assertEquals("Failed sellIn for Sulfuras, Hand of Ragnaros", 100, sellIn);
	}
	
	@Test
	public void test_LoopTest() {
		// 0 loops
		GildedRose inn = new GildedRose();
		inn.oneDay(); // 0 loops
		
		List<Item> items = inn.getItems();
		
		assertEquals("Failed", 0, items.size());
		
		// 1 loop
		inn.setItem(new Item("+5 Dexterity Vest", 10, 20));
		inn.oneDay(); // 1 loop
		items = inn.getItems();
		
		int quality = items.get(0).getQuality();
		int sellIn = items.get(0).getSellIn();
		
		assertEquals("Failed quality for Dexterity Vest", 19, quality);
		assertEquals("Failed sellIn for Dexterity Vest", 9, sellIn);
		
		// 2 Loops
		inn.setItem(new Item("Aged Brie", 10, 20));
		inn.oneDay(); // 2 loops
		
		items = inn.getItems();
		
		quality = items.get(0).getQuality();
		sellIn = items.get(0).getSellIn();
		
		assertEquals("Failed quality for Dexterity Vest", 18, quality);
		assertEquals("Failed sellIn for Dexterity Vest", 8, sellIn);
		
		quality = items.get(1).getQuality();
		sellIn = items.get(1).getSellIn();
		
		assertEquals("Failed quality for Aged Brie", 21, quality);
		assertEquals("Failed sellIn for Aged Brie", 9, sellIn);
		
		
		
		// 3 Loops
		inn.setItem(new Item("Sulfuras, Hand of Ragnaros", 0, 80));
		inn.oneDay(); // 3 loops
		
		items = inn.getItems();
		
		quality = items.get(0).getQuality();
		sellIn = items.get(0).getSellIn();
		
		assertEquals("Failed quality for Dexterity Vest", 17, quality);
		assertEquals("Failed sellIn for Dexterity Vest", 7, sellIn);
		
		quality = items.get(1).getQuality();
		sellIn = items.get(1).getSellIn();
		
		assertEquals("Failed quality for Aged Brie", 22, quality);
		assertEquals("Failed sellIn for Aged Brie", 8, sellIn);
		
		quality = items.get(2).getQuality();
		sellIn = items.get(2).getSellIn();
		
		assertEquals("Failed quality for Sulfuras", 80, quality);
		assertEquals("Failed sellIn for Sulfuras", 0, sellIn);
		
		
		// 4 Loops
		inn.setItem(new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20));
		inn.oneDay(); // 4 loops
		
		items = inn.getItems();
		
		quality = items.get(0).getQuality();
		sellIn = items.get(0).getSellIn();
		
		assertEquals("Failed quality for Dexterity Vest", 16, quality);
		assertEquals("Failed sellIn for Dexterity Vest", 6, sellIn);
		
		quality = items.get(1).getQuality();
		sellIn = items.get(1).getSellIn();
		
		assertEquals("Failed quality for Aged Brie", 23, quality);
		assertEquals("Failed sellIn for Aged Brie", 7, sellIn);
		
		quality = items.get(2).getQuality();
		sellIn = items.get(2).getSellIn();
		
		assertEquals("Failed quality for Sulfuras", 80, quality);
		assertEquals("Failed sellIn for Sulfuras", 0, sellIn);
		
		quality = items.get(3).getQuality();
		sellIn = items.get(3).getSellIn();
		
		assertEquals("Failed quality for Sulfuras", 21, quality);
		assertEquals("Failed sellIn for Sulfuras", 14, sellIn);
	}
	
}
