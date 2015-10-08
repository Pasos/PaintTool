import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

//引用元
//https://github.com/iga-c/BlendImage

/**
 * 画像をブレンドモードで合成する。
 *
 * 参考:
 * ・http://d.hatena.ne.jp/yus_iri/20110921/1316610121
 * ・http://docs.gimp.org/2.8/ja/gimp-concepts-layer-modes.html
 * ・http://ofo.jp/osakana/cgtips/blendmode.phtml
 * ・http://www.cg-ya.net/2dcg/aboutimage/composite-is-math/
 *
 * @author Igari Kazuya
 */
public class Blend {
	/**
	 * ブレンドモードの列挙型
	 * 各色に対して行う処理を書いておく
	 *
	 * @author Igari Kazuya
	 */
	public enum Mode{
		/**
		 * 通常
		 */
		MODE_NORMAL{
			@Override
			public int calc(int mask, int background) {
				return mask;
			}
		},

		/**
		 * 乗算
		 */
		MODE_MULTIPLE{
			@Override
			public int calc(int mask, int background) {
				return colorRange(mask * background / 255);
			}
		},

		/**
		 * スクリーン
		 */
		MODE_SCREEN{
			@Override
			public int calc(int mask, int background) {
				return colorRange(mask + background - mask * background / 255);
			}
		},

		/**
		 * オーバーレイ
		 */
		MODE_OVERLAY{
			@Override
			public int calc(int mask, int background) {
				if(background < 128){
					return colorRange(background * mask * 2 / 255);
				}else{
					return colorRange(2 * (background + mask - background * mask / 255) - 255);
				}
			}
		},

		/**
		 * ソフトライト
		 */
		MODE_SOFTRIGHT{
			@Override
			public int calc(int mask, int background) {
				if(background < 128){
					return colorRange( (int)(Math.pow((mask/255.0),((255-background) / 128.0)) * 255) );
				}else{
					return colorRange( (int)(Math.pow((mask/255.0), (128.0/background)) * 255));
				}
			}
		},

		/**
		 * ハードライト
		 */
		MODE_HARDLIGHT{
			@Override
			public int calc(int mask, int background) {
				if(background < 128){
					return colorRange( mask * background * 2 / 255 );
				}else{
					return colorRange( 2 * ( mask + background - mask * background / 255) - 255);
				}
			}
		},

		/**
		 * 覆い焼き
		 */
		MODE_DODGE{
			@Override
			public int calc(int mask, int background) {
				return background == 255 ? 255 : colorRange(mask * 255 / (255 - background));
			}
		};

		/**
		 * 各ピクセルの各色の画素に対しブレンドの計算を行う
		 *
		 * @param mask 上側の画像のピクセルの色
		 * @param background 下側の画像のピクセルの色
		 * @return 計算結果
		 */
		public abstract int calc(int mask,int background);

		/**
		 * RGB値が0から255の値に収まらないといけないのではみ出ている場合は補正する
		 *
		 * @param num ブレンド後の返す色
		 * @return はみ出していた場合は補正した後の色を、はみ出していない場合はそのままの色を返す
		 */
		private static int colorRange(int num){
			return Math.min(255,Math.max(0, num));
		}
	}

	/**
	 * lowerの画像に対してmaskの画像を左上の座標(SX,SY)からブレンドする。
	 * ブレンドの方法はmodeに渡された列挙型で判断する。
	 *
	 * @param upper 上に合成される画像
	 * @param lower 下に合成される画像
	 * @param upperSX 上側の画像の開始X座標
	 * @param upperSY 上側の画像の開始Y座標
	 * @param mode ブレンドモード
	 * @return ブレンド後のBufferedImage
	 */
	public static BufferedImage execute(BufferedImage upper, BufferedImage lower, int upperSX, int upperSY, Mode mode){
		for(int i=upperSY; i<upperSY+upper.getHeight(); i++){
			for(int j=upperSX; j<upperSX+upper.getWidth(); j++){
				if(0 <= i && 0 <= j && j < lower.getWidth() && i < lower.getHeight()){
					List<Integer> uppercolor = getRGBColorList(upper.getRGB(j-upperSX, i-upperSY));
					List<Integer> lowercolor = getRGBColorList(lower.getRGB(j, i));
					int r = mode.calc(uppercolor.get(1), lowercolor.get(1));
					int g = mode.calc(uppercolor.get(2), lowercolor.get(2));
					int b = mode.calc(uppercolor.get(3), lowercolor.get(3));
					int c = lowercolor.get(0)<<24 | r<<16 | g<<8 | b;

					lower.setRGB(j, i, c);
				}
			}
		}

		return lower;
	}

	/**
	 * lowerの画像に対してupperの画像をmaskの画像が白の位置のみブレンドする。
	 * ブレンドの方法はmodeに渡された列挙型で判断する。
	 * ただし、全ての画像サイズが一致していないと使用できない。
	 *
	 * @param upper 上に合成される画像
	 * @param lower 下に合成される画像
	 * @param mask 白黒の画像 マスクが白の位置のみ合成する
	 * @param mode ブレンドモード
	 * @return ブレンド後のBufferedImage 画像サイズが違う場合はnullを返す。
	 */
	public static BufferedImage execute(BufferedImage upper, BufferedImage lower, BufferedImage mask, Mode mode){
		if(upper.getWidth() != lower.getWidth() || lower.getWidth() != mask.getWidth()) return null;
		if(upper.getHeight() != lower.getHeight() || lower.getHeight() != mask.getHeight()) return null;

		for(int i=0; i<lower.getHeight(); i++){
			for(int j=0; j<lower.getWidth(); j++){
				int maskrgb = mask.getRGB(j, i);
				if((maskrgb & 0xffffff) == 0xffffff){
					List<Integer> uppercolor = getRGBColorList(upper.getRGB(j, i));
					List<Integer> lowercolor = getRGBColorList(lower.getRGB(j, i));
					int r = mode.calc(uppercolor.get(1), lowercolor.get(1));
					int g = mode.calc(uppercolor.get(2), lowercolor.get(2));
					int b = mode.calc(uppercolor.get(3), lowercolor.get(3));
					int c = lowercolor.get(0)<<24 | r<<16 | g<<8 | b;

					lower.setRGB(j, i, c);
				}
			}
		}

		return lower;
	}

	/**
	 * 色の変数を画素のリストに変換する。
	 *
	 * @param color 1ピクセル単位の色
	 * @return 画素のリスト。[0]->A, [1]->R, [2]->G, [3]->Bの値が入っている
	 */
	private static List<Integer> getRGBColorList(int color){
		List<Integer> res = new ArrayList<Integer>();
		int a = (color >>> 24) & 0xff;
		int r = (color >>> 16) & 0xff;
		int g = (color >>>  8) & 0xff;
		int b = color          & 0xff;
		res.add(a); res.add(r); res.add(g); res.add(b);
		return res;
	}
}
