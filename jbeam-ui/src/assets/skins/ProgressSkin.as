package assets.skins
{
	import flash.display.Graphics;
	
	import mx.core.BitmapAsset;
	import mx.skins.halo.ProgressMaskSkin;

	public class ProgressSkin extends ProgressMaskSkin
	{
		[Embed(source="assets/images/pro_bar_bg.png")]
		private var img:Class;
		
		public function ProgressSkin()
		{
			super();
		}
		
		override protected function updateDisplayList(w:Number, h:Number):void
		{
			super.updateDisplayList(w, h);
			var imgClsBR:Class   = Class( img );
			var bgImageBR:BitmapAsset = new imgClsBR();	          
			var g:Graphics = graphics;  
			g.clear();
			g.beginBitmapFill(bgImageBR.bitmapData,null,true);	        
			g.drawRect(1, 1, w - 2, h - 2);	        
			g.endFill();
		}
	}
}